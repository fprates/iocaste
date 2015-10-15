package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    public int counter;
    public Map<String, Map<String, String>> handlers;
    
    public Services() {
        export("test", new Test());
        export("connect", new Connect());
        export("connection_data_get", new GetConnectionData());
        export("disconnect", new Disconnect());
        export("structures_function_get", new GetFunctionStructures());
        export("server_register", new Register());
        export("handler_install", new Install());
        handlers = new HashMap<>();
    }
}

class Register extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String name = message.getString("name");
        Services function = getFunction();
        
        if (!function.handlers.containsKey(name))
            function.handlers.put(name, new HashMap<String, String>());
        return null;
    }
    
}

class Install extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String server = message.getString("server");
        String name = message.getString("name");
        Services function = getFunction();
        
        function.handlers.get(server).put(name, null);
        return null;
    }
    
}
