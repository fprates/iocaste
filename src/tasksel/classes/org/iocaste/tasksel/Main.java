package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.portal.AbstractPortalBuilder;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.tasksel.groups.GroupsPanelPage;
import org.iocaste.tasksel.tasks.TasksPanelPage;

public class Main extends AbstractPortalBuilder {
    public GroupsPanelPage page;
    
    public Main() {
        export("task_redirect", new Redirect());
        export("refresh", new Refresh());
    }

    @Override
    protected final PortalContext contextInstance(PageBuilderContext context) {
        return new Context(context);
    }
    
    @Override
    public final void config(PortalContext portalctx) {
        portalctx.getContext().messages = new Messages();
        portalctx.nologin = true;
        instance("main", page = new GroupsPanelPage());
        instance("tasks", new TasksPanelPage());
    }
    
    @Override
    protected final void installConfig(PageBuilderDefaultInstall defaultinstall)
    {
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
