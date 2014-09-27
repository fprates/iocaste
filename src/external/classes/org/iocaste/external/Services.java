package org.iocaste.external;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("dispatch", "dispatch");
    }
    
    public final Object dispatch(Message message) throws Exception {
        Object object;
        Message appmessage;
        String app, function, username, secret;
        boolean connected;
        GenericService service;
        Iocaste iocaste = new Iocaste(this);
        
        username = message.getString("username");
        secret = message.getString("secret");
        connected = iocaste.login(username, secret, "pt_BR");
        if (!connected)
            return null;
        
        app = message.get("app");
        function = message.get("function");
        service = new GenericService(this, app);
        
        appmessage = new Message(function);
        appmessage.add("stream", message.get("stream"));
        try {
            object = service.invoke(appmessage);
        } catch (Exception e) {
            iocaste.rollback();
            throw e;
        }
        
        iocaste.commit();
        iocaste.disconnect();
        return object;
    }
}
