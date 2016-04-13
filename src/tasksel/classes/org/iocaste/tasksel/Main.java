package org.iocaste.tasksel;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.PanelPageItem;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.appbuilder.common.panel.dashboard.StandardDashboardConfig;
import org.iocaste.appbuilder.common.panel.dashboard.StandardDashboardSpec;
import org.iocaste.appbuilder.common.style.CommonStyle;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Iocaste;

public class Main extends AbstractPageBuilder {
    public static final String MAIN = "main";
    public Map<String, Set<TaskEntry>> groups;
    public PageBuilderContext context;
    public TaskPanelPage page;
    
    public Main() {
        export("task_redirect", new Redirect());
        export("refresh", new Refresh());
    }

    @Override
    public final void config(PageBuilderContext context) {
        StandardPanel panel;
        CommonStyle profile;
        Context extcontext;
        
        extcontext = new Context(context);
        groups = getLists();
        this.context = context;

        page = new TaskPanelPage();
        page.function = this;
        
        panel = new StandardPanel(context);
        panel.instance(MAIN, page, extcontext);
        
        profile = CommonStyle.get();
        profile.content.bgcolor = "#202020";
        profile.head.bgcolor = "#3030ff";
    }
    
    /**
     * 
     * @param context
     * @return
     */
    private final Map<String, Set<TaskEntry>> getLists() {
        Query query;
        Set<TaskEntry> entries;
        TaskEntry entry;
        ExtendedObject[] result, mobject;
        Map<String, Set<TaskEntry>> lists;
        String groupname, language, taskname, username;
        Iocaste iocaste = new Iocaste(this);
        Documents documents = new Documents(this);
        
        username = iocaste.getUsername();
        query = new Query();
        query.addColumns(
                "TASK_ENTRY.GROUP", "TASK_ENTRY.NAME", "TASK_ENTRY.ID");
        query.setModel("USER_TASKS_GROUPS");
        query.join("TASK_ENTRY", "USER_TASKS_GROUPS.GROUP", "GROUP");
        query.andEqual("USERNAME", username);
        result = documents.select(query);
        if (result == null)
            return null;
        
        language = iocaste.getLocale().toString(); 
        lists = new LinkedHashMap<>();
        for (ExtendedObject object : result) {
            groupname = object.get("GROUP");
            if (lists.containsKey(groupname)) {
                entries = lists.get(groupname);
            } else {
                entries = new LinkedHashSet<>();
                lists.put(groupname, entries);
            }
            
            taskname = object.get("NAME");
            entry = new TaskEntry();
            entry.setName(taskname);
            entries.add(entry);
            
            query = new Query();
            query.setModel("TASK_ENTRY_TEXT");
            query.andEqual("ENTRY", taskname);
            query.andEqual("LANGUAGE", language);
            query.setMaxResults(1);
            mobject = documents.select(query);
            if (mobject != null)
                entry.setText((String)mobject[0].get("TEXT"));
        }
        
        return lists;
    }
    
    @Override
    protected final void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setProfile("BASE");
        defaultinstall.setProgramAuthorization("TASKSEL.EXECUTE");
        installObject("main", new InstallObject());
    }
    
    public final void reassignActions() {
        reassignCustomActions(context);
    }
    
    public final void refresh() {
        ViewContext view;
        
        groups = getLists();
        view = context.getView(Main.MAIN);
        view.getSpec().setInitialized(false);
        
        setReloadableView(true);
        page.refresh();
        
        StandardPanel.reassignActions(view, page);
        reassignActions();
    }
}

class TaskPanelPage extends AbstractPanelPage {
    public Main function;
    
    public final void execute() {
        set(new StandardDashboardSpec(this));
        set(new StandardDashboardConfig(this));
        update();
        refresh();
    }
    
    public final void refresh() {
        PanelPageItem item;
        String text;
        Set<TaskEntry> entries;
        
        function.page = this;
        items.clear();
        for (String name : function.groups.keySet()) {
            item = instance(name);
            entries = function.groups.get(name);
            for (TaskEntry entry : entries) {
                text = entry.getText();
                if (text == null)
                    text = entry.getName();
                
                item.context.call(text, entry.getName());
            }
        }
    }
}