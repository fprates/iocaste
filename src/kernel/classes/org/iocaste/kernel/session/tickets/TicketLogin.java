package org.iocaste.kernel.session.tickets;

import java.util.Map;

import org.iocaste.kernel.database.CheckedSelect;
import org.iocaste.kernel.session.Login;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.AccessTicket;

public class TicketLogin extends AbstractHandler {
    
    @SuppressWarnings("unchecked")
    private final AccessTicket get(String id) throws Exception {
        AccessTicket ticket;
        Map<String, Object> record;
        Object[] lines;
        CheckedSelect select;
        Session session = getFunction();
        
        select = session.database.get("checked_select");
        
        /*
         * se precisa explicar código com comentário,
         * é porque não é algo bom:
         * usaremos try/catch aqui. é possível que SHELL004 ainda não
         * esteja instalada. Exception é bem ampla, mas execute() não
         * lança SQLException. Vamos negociar assim, por enquanto.
         */
        try {
            lines = select.run(session.database, null, "SHELL004",
                    "where TKTID = ?", new Object[] {id}, null);
        } catch (Exception e) {
            return null;
        }
        
        if (lines == null)
            return null;
        
        for (Object line : lines) {
            record = (Map<String, Object>)line;
            ticket = new AccessTicket();
            ticket.setAppname((String)record.get("APPNM"));
            ticket.setPagename((String)record.get("PAGEN"));
            ticket.setUsername((String)record.get("USRNM"));
            ticket.setSecret((String)record.get("SECRT"));
            ticket.setLocale((String)record.get("LOCAL"));
            return ticket;
        }
        return null;
    }

    @Override
    public Object run(Message message) throws Exception {
        String ticketid = message.getst("ticket_id");
        String sessionid = message.getSessionid();
        AccessTicket ticket = get(ticketid);
        Login login = getFunction().get("login");
        
        return login.run(sessionid,
                ticket.getUsername(), ticket.getSecret(), ticket.getLocale());
    }
    
}
