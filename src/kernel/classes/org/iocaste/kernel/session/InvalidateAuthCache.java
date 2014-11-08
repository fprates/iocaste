package org.iocaste.kernel.session;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class InvalidateAuthCache extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session = getFunction();
        
        for (String sessionid : session.sessions.keySet())
            session.sessions.get(sessionid).setAuthorizations(null);
        return null;
    }

}
