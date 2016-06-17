package org.iocaste.kernel.session;

import java.util.List;

import org.iocaste.kernel.UserContext;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class Disconnect extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        GenericService service;
        Session session;
        UserContext context;
        List<EventHandler> handlers;
        String sessionid = message.getSessionid();

        if (sessionid == null)
            throw new IocasteException("Null session not allowed.");
        
        session = getFunction();
        if (!session.sessions.containsKey(sessionid))
            return null;
        
        handlers = session.events.get(Iocaste.DISCONNECT_EVENT);
        if (handlers != null)
            for (EventHandler handler : handlers) {
                service = new GenericService(session, handler.url);
                service.invoke(new Message(handler.function));
            }
        context = session.sessions.get(sessionid);
        session.usersessions.remove(context.getUser().getUsername());
        session.sessions.remove(sessionid);
        return null;
    }

}
