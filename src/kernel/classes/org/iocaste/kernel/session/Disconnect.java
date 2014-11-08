package org.iocaste.kernel.session;

import org.iocaste.kernel.UserContext;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class Disconnect extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session;
        UserContext context;
        String sessionid = message.getSessionid();

        if (sessionid == null)
            throw new IocasteException("Null session not allowed.");
        
        session = getFunction();
        if (!session.sessions.containsKey(sessionid))
            return null;
        
        context = session.sessions.get(sessionid);
        session.usersessions.remove(context.getUser().getUsername());
        session.sessions.remove(sessionid);
        return null;
    }

}
