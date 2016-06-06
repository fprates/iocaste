package org.iocaste.external.handlers;

import org.iocaste.external.Services;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class Install extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String server = message.getst("server");
        String name = message.getst("name");
        Services function = getFunction();
        
        function.handlers.get(server).put(name, null);
        return null;
    }
    
}
