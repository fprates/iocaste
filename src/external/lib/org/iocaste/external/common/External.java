package org.iocaste.external.common;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class External extends AbstractServiceInterface {
    private static final String SERVICE = "/iocaste-external/services.html";
    private Function function;
    
    public External(Function function) {
        this.function = function;
        initService(function, SERVICE);
    }
    
    public final boolean connect(String user, String secret, String locale) {
        Message message;
        String sessionid;
        
        message = new Message("connect");
        message.add("user", user);
        message.add("secret", secret);
        message.add("locale", locale);
        sessionid = call(message);
        if (sessionid == null)
            return false;
        
        function.setSessionid(sessionid);
        initService(function, SERVICE);
        return true;
    }
    
    public final ComplexDocument getConnectionData(String name) {
        Message message;
        
        message = new Message("connection_data_get");
        message.add("name", name);
        return call(message);
    }
}
