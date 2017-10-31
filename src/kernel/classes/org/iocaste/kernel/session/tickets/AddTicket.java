package org.iocaste.kernel.session.tickets;

import java.util.UUID;

import org.iocaste.kernel.database.Update;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.AccessTicket;

public class AddTicket extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session = getFunction();
        AccessTicket ticket = message.get("ticket");
        String ticketcode = UUID.randomUUID().toString();
        Update update = session.database.get("update");
        String sessionid = message.getSessionid();
        
        session.tickets.put(ticketcode, ticket);
        update.run(session.database.getDBConnection(sessionid),
            Session.QUERIES[Session.INSERT],
            ticketcode,
            ticket.getAppname(),
            ticket.getPagename(),
            ticket.getUsername(),
            ticket.getSecret(),
            ticket.getLocale());
        
        return ticketcode;
    }
    
}
