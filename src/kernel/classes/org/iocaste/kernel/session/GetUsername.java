package org.iocaste.kernel.session;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetUsername extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session;
        String sessionid = message.getSessionid();
        
        if (sessionid == null)
            throw new IocasteException("Null session not allowed.");
        
        session = getFunction();
        if (!session.sessions.containsKey(sessionid))
            return null;
        
        return session.sessions.
                get(sessionid).usercontext.getUser().getUsername();
    }

}
