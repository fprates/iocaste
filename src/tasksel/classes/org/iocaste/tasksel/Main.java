package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.tasksel.groups.GroupsPanelPage;
import org.iocaste.tasksel.tasks.TasksPanelPage;

public class Main extends AbstractPageBuilder {
    public GroupsPanelPage page;
    private Context extcontext;
    
    public Main() {
        export("task_redirect", new Redirect());
        export("refresh", new Refresh());
    }

    @Override
    public final void config(PageBuilderContext context) {
        context.messages = new Messages();
        context.add("main", page = new GroupsPanelPage(),
                extcontext = new Context(context));
        context.add("tasks", new TasksPanelPage(), extcontext);
    }
    
    @Override
    protected final void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setProfile("BASE");
        defaultinstall.setProgramAuthorization("TASKSEL.EXECUTE");
        installObject("main", new InstallObject());
    }
    
    public final void refresh() throws Exception {
        PageBuilderContext context = getContext();
        
        context.run("main", "lists_get");
        context.getView("main").getSpec().setInitialized(false);
        setReloadableView(true);
    }
}
