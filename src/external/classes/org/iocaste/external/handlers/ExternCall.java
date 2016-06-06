package org.iocaste.external.handlers;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.StandardService;

public class ExternCall extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        StandardService service;
        String address = message.getst("address");
        int port = message.geti("port");
        
        service = new StandardService(new ExternCallStream(address, port));
        return service.call(message.get("message"));
    }
    
}
