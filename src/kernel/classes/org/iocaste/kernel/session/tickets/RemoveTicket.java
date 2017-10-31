package org.iocaste.kernel.session.tickets;

import org.iocaste.kernel.database.Update;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class RemoveTicket extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session = getFunction();
        Update update = session.database.get("update");
        String ticket = message.getst("ticket");
        
        session.tickets.remove(ticket);
        update.run(session.database.getDBConnection(message.getSessionid()),
                Session.QUERIES[Session.DELETE], ticket);
        return null;
    }
    
}
