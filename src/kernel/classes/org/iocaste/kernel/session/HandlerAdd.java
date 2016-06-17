package org.iocaste.kernel.session;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class HandlerAdd extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        byte code = message.getb("event");
        Session session = getFunction();
        EventHandler handler = new EventHandler();
        
        handler.url = message.getst("url");
        handler.function = message.getst("function");
        session.add(code, handler);
        return null;
    }
    
}
