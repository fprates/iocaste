package org.iocaste.workbench.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Workbench extends AbstractServiceInterface {
    public static final String SERVICENAME = "/iocaste-workbench/services.html";
    
    public Workbench(Function function) {
        initService(function, SERVICENAME);
    }
    
    public final void build(String file) {
        Message message = new Message("build");
        message.add("file", file);
        call(message);
    }
    
    public final String fetch(String file) {
        Message message = new Message("fetch");
        message.add("file", file);
        return call(message);
    }
}
