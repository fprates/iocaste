package org.iocaste.kernel.session;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class IsConnected extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session;
        String sessionid = message.getSessionid();
        
        if (sessionid == null)
            return false;
        
        session = getFunction();
        return session.sessions.containsKey(sessionid);
    }
}
