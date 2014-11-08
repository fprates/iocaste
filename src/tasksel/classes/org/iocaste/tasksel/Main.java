package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class Main extends AbstractPageBuilder {
    public static final String MAIN = "main";
    private Context extcontext;
    
    public Main() {
        export("task_redirect", "redirect");
        export("refresh", "refresh");
    }

    public void config(PageBuilderContext context) {
        ViewContext viewctx;
        
        extcontext = new Context();
        extcontext.context = context;
        extcontext.groups = Response.getLists(context);
        
        viewctx = context.instance(MAIN);
        viewctx.set(extcontext);
        viewctx.set(new TasksSpec());
        viewctx.set(new TasksConfig());
        viewctx.set(new TasksInput());
        for (String name : extcontext.groups.keySet())
            viewctx.put(name, new Call(name));
        viewctx.setUpdate(true);
    }
    
    public final ViewState redirect(Message message) {
        ViewState state;
        String task = message.getString("task");
        
        state = new ViewState();
        state.view = new View(null, null);
        if (Request.call(this, state.view, task) == 1)
            return null;
        
        state.rapp = getRedirectedApp();
        state.rpage = getRedirectedPage();
        state.parameters = getParameters();
        return state;
    }

    public final void refresh(Message message) {
        extcontext.groups = Response.getLists(extcontext.context);
        extcontext.context.getView(MAIN).getSpec().setInitialized(false);
        setReloadableView(true);
    }
    
    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setProfile("BASE");
        installObject("main", new InstallObject());
    }
}