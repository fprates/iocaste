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
        
        if (Common.call(extcontext.function, task) == 1)
            return null;

        state = new ViewState();
        state.view = new View(null, null);
        state.rapp = extcontext.function.getRedirectedApp();
        state.rpage = extcontext.function.getRedirectedPage();
        state.parameters = extcontext.function.getParameters();
        return state;
    }

}
