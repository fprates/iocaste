package org.iocaste.tasksel;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class Redirect extends AbstractHandler {
    public Context extcontext;
    
    @Override
    public Object run(Message message) throws Exception {
        ViewState state = new ViewState();
        String task = message.getString("task");

        switch (Common.call(extcontext.function, task)) {
        case 0:
            state.view = new View(null, null);
            state.rapp = extcontext.function.getRedirectedApp();
            state.rpage = extcontext.function.getRedirectedPage();
            state.parameters = extcontext.function.getParameters();
            break;
        default:
            state.error = 1;
            break;
        }
        
        return state;
    }

}
