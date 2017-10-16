package org.iocaste.kernel.session;

import java.util.Map;

import org.iocaste.kernel.database.CheckedSelect;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.AccessTicket;

public class LoadTickets extends AbstractHandler {
    private boolean loaded;

    @SuppressWarnings("unchecked")
    @Override
    public Object run(Message message) throws Exception {
        AccessTicket ticket;
        Map<String, Object> record;
        Object[] lines;
        CheckedSelect select;
        Session session = getFunction();
        
        if (loaded)
            return null;
        
        select = session.database.get("checked_select");
        
        /*
         * se precisa explicar código com comentário,
         * é porque não é algo bom:
         * usaremos try/catch aqui. é possível que SHELL004 ainda não
         * esteja instalada. Exception é bem ampla, mas execute() não
         * lança SQLException. Vamos negociar assim, por enquanto.
         */
        try {
            lines = select.run(
                    session.database, null, "SHELL004", null, null, null); 
        } catch (Exception e) {
            return null;
        }
        
        loaded = true;
        if (lines == null)
            return null;
        
        session.tickets.clear();
        for (Object line : lines) {
            record = (Map<String, Object>)line;
            ticket = new AccessTicket();
            ticket.setAppname((String)record.get("APPNM"));
            ticket.setPagename((String)record.get("PAGEN"));
            ticket.setUsername((String)record.get("USRNM"));
            ticket.setSecret((String)record.get("SECRT"));
            ticket.setLocale((String)record.get("LOCAL"));
            session.tickets.put((String)record.get("TKTID"), ticket);
        }
        return null;
    }
    
}
