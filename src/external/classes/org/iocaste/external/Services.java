package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.external.handlers.Connect;
import org.iocaste.external.handlers.Disconnect;
import org.iocaste.external.handlers.ExternCall;
import org.iocaste.external.handlers.GetConnectionData;
import org.iocaste.external.handlers.GetFunctionStructures;
import org.iocaste.external.handlers.Install;
import org.iocaste.external.handlers.Register;
import org.iocaste.external.handlers.Test;
import org.iocaste.protocol.AbstractFunction;

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
        export("extern_call", new ExternCall());
        handlers = new HashMap<>();
    }
}
