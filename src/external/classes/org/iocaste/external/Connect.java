package org.iocaste.external;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Connect extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Iocaste iocaste;
        String sessionid;
        Services services = getFunction();
        String user = message.getString("user");
        String secret = message.getString("secret");
        String locale = message.getString("locale");
        
        sessionid = new StringBuilder(message.getSessionid()).
                append(":").append(services.counter+1).toString();

        services.setSessionid(sessionid);
        iocaste = new Iocaste(services);
        if (!iocaste.login(user, secret, locale))
            return null;
        
        services.counter++;
        return sessionid;
    }

}
