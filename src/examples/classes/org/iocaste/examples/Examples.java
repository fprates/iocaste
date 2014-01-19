package org.iocaste.examples;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Examples extends AbstractServiceInterface {
    private static final String SERVERNAME = "/iocaste-examples/services.html";
    
    public Examples(Function function) {
        initService(function, SERVERNAME);
    }
    
    public final int ping() {
        Message message = new Message();
        
        message.setId("ping");
        return call(message);
    }
}
