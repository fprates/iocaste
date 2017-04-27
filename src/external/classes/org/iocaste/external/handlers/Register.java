package org.iocaste.external.handlers;

import java.util.HashMap;

import org.iocaste.external.Services;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class Register extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String name = message.getst("name");
        Services function = getFunction();
        
        if (!function.handlers.containsKey(name))
            function.handlers.put(name, new HashMap<String, String>());
        return null;
    }
    
}
