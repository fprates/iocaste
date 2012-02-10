package org.iocaste.transport.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Transport extends AbstractServiceInterface {
    public static final String SERVERNAME = "/iocaste-transport/service.html";

    public Transport(Function function) {
        initService(function, SERVERNAME);
    }
    
    public final void save(Order order) throws Exception {
        Message message = new Message();
        
        message.setId("save");
        message.add("order", order);
        
        call(message);
    }
}
