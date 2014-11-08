package org.iocaste.tasksel;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class Redirect extends AbstractHandler {
    public Context extcontext;
    
    @Override
    public Object run(Message message) throws Exception {
        ViewState state;
        String task = message.getString("task");
        
        state = new ViewState();
        state.view = new View(null, null);
        if (Common.call(extcontext.context.function, state.view, task) == 1)
            return null;
        
        state.rapp = extcontext.context.function.getRedirectedApp();
        state.rpage = extcontext.context.function.getRedirectedPage();
        state.parameters = extcontext.context.function.getParameters();
        return state;
    }

}
