package org.iocaste.tasksel;

import java.util.LinkedHashSet;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Iocaste;
import org.iocaste.tasksel.groups.GroupsPanelPage;
import org.iocaste.tasksel.tasks.TasksPanelPage;

public class Main extends AbstractPageBuilder {
    public static final String MAIN = "main";
    public Set<ExtendedObject> entries;
    public PageBuilderContext context;
    public GroupsPanelPage page;
    
    public Main() {
        export("task_redirect", new Redirect());
        export("refresh", new Refresh());
    }

    @Override
    public final void config(PageBuilderContext context) {
        StandardPanel panel;
        Context extcontext;
        AbstractPanelPage tasks;
        
        extcontext = new Context(context);
        entries = getLists();
        this.context = context;

        page = new GroupsPanelPage();
        
        panel = new StandardPanel(context);
        panel.instance(MAIN, page, extcontext);
        
        tasks = new TasksPanelPage();
        panel = new StandardPanel(context);
        panel.instance("tasks", tasks, extcontext);
    }
    
    /**
     * 
     * @param context
     * @return
     */
    private final Set<ExtendedObject> getLists() {
        Query query;
        ExtendedObject entry;
        ExtendedObject[] result, mobject;
        Set<ExtendedObject> entries;
        String groupname, language, taskname, username;
        DocumentModel model;
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
        
        model = documents.getModel("TASK_TILE_ENTRY");
        language = iocaste.getLocale().toString(); 
        entries = new LinkedHashSet<>();
        for (ExtendedObject object : result) {
            groupname = object.get("GROUP");
            taskname = object.getst("NAME");
            
            query = new Query();
            query.setModel("TASK_ENTRY_TEXT");
            query.andEqual("ENTRY", taskname);
            query.andEqual("LANGUAGE", language);
            query.setMaxResults(1);
            mobject = documents.select(query);
            
            entry = new ExtendedObject(model);
            entry.set("GROUP", groupname);
            entry.set("NAME", taskname);
            if (mobject != null)
                entry.set("TEXT", mobject[0].getst("TEXT"));
            entries.add(entry);
        }
        
        return entries;
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
        
        entries = getLists();
        view = context.getView(Main.MAIN);
        view.getSpec().setInitialized(false);
        
        setReloadableView(true);
        
        StandardPanel.reassignActions(view, page);
        reassignActions();
    }
}
