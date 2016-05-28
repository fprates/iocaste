package org.iocaste.tasksel;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class Redirect extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        ViewState state = new ViewState();
        String task = message.getst("task");
        AbstractPage function = getFunction();
        
        switch (Common.call(function, task)) {
        case 0:
            state.view = new View(null, null);
            state.rapp = function.getRedirectedApp();
            state.rpage = function.getRedirectedPage();
            state.parameters = function.getParameters();
            break;
        default:
            state.error = 1;
            break;
        }
        
        return state;
    }

}
