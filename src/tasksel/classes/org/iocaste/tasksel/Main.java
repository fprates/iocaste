package org.iocaste.tasksel;

import java.util.Set;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.PanelPageItem;
import org.iocaste.appbuilder.common.panel.StandardPanel;

public class Main extends AbstractPageBuilder {
    public static final String MAIN = "main";
    private Context extcontext;
    
    public Main() {
        Redirect redirect;
        Refresh refresh;
        
        export("task_redirect", redirect = new Redirect());
        export("refresh", refresh = new Refresh());
        extcontext = new Context();
        extcontext.function = this;
        redirect.extcontext = refresh.extcontext = extcontext;
    }

    @Override
    public void config(PageBuilderContext context) {
        StandardPanel panel;
        
        extcontext.context = context;
        extcontext.groups = Refresh.getLists(context);
        
        panel = new StandardPanel(context);
        panel.instance(MAIN, new TaskPanelPage(), extcontext);
    }
    
    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setProfile("BASE");
        defaultinstall.setProgramAuthorization("TASKSEL.EXECUTE");
        installObject("main", new InstallObject());
    }
}

class TaskPanelPage extends AbstractPanelPage {
    
    public final void execute() {
        PanelPageItem item;
        String text;
        Set<TaskEntry> entries;
        Context extcontext;
        
        extcontext = getExtendedContext();
        for (String name : extcontext.groups.keySet()) {
            item = instance(name);
            entries = extcontext.groups.get(name);
            for (TaskEntry entry : entries) {
                text = entry.getText();
                if (text == null)
                    text = entry.getName();
                
                item.context.call(text, entry.getName());
            }
        }
    }
}