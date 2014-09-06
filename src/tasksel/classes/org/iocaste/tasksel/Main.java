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
    public Main() {
        export("task_redirect", "redirect");
    }

    public void config(PageBuilderContext context) {
        ViewContext viewctx;
        Context extcontext = new Context();
        
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
        return state;
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        // TODO Stub de método gerado automaticamente
        
    }
}