package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;

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
        ViewContext viewctx;
        
        extcontext.context = context;
        extcontext.groups = Refresh.getLists(context);
        
        viewctx = context.instance(MAIN);
        viewctx.set(extcontext);
        viewctx.set(new TasksSpec());
        viewctx.set(new TasksConfig());
        viewctx.set(new TasksInput());
        for (String name : extcontext.groups.keySet())
            viewctx.put(name, new Call(name));
        viewctx.setUpdate(true);
    }
    
    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setProfile("BASE");
        defaultinstall.setProgramAuthorization("TASKSEL.EXECUTE");
        installObject("main", new InstallObject());
    }
}