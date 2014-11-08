package org.iocaste.tasksel;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class Refresh extends AbstractHandler {
    public Context extcontext;
    
    @Override
    public Object run(Message message) throws Exception {
        extcontext.groups = Response.getLists(extcontext.context);
        extcontext.context.getView(Main.MAIN).getSpec().setInitialized(false);
        extcontext.context.function.setReloadableView(true);
        return null;
    }

}
