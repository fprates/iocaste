package org.iocaste.shell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AccessTicket;

public class TicketControl {
    private boolean loaded;
    private Map<String, AccessTicket> tickets;
    private byte INSERT = 0;
    private byte DELETE = 1;
    private String[] QUERIES = {
        "insert into SHELL004(TKTID, APPNM, PAGEN, USRNM, SECRT, LOCAL)"
            + " values (?, ?, ?, ?, ?, ?)",
        "delete from SHELL004 where TKTID = ?"
    };
    
    public TicketControl() {
        tickets = new HashMap<>();
    }
    
    public final String add(AccessTicket ticket, Function function) {
        String ticketcode = UUID.randomUUID().toString();
            
        tickets.put(ticketcode, ticket);
        new Iocaste(function).update(QUERIES[INSERT], ticketcode,
                ticket.getAppname(),
                ticket.getPagename(),
                ticket.getUsername(),
                ticket.getSecret(),
                ticket.getLocale());
        
        return ticketcode;
    }
    
    public final AccessTicket get(String ticketcode) {
        return tickets.get(ticketcode);
    }
    
    public final boolean has(String ticketcode) {
        return tickets.containsKey(ticketcode);
    }
    
    @SuppressWarnings("unchecked")
    public final void load(Function function) {
        AccessTicket ticket;
        Map<String, Object> record;
        Object[] lines;
        CheckedSelect select;
        
        if (loaded)
            return;
        
        select = new CheckedSelect(function);
        select.setFrom("SHELL004");
        
        /*
         * se precisa explicar código com comentário,
         * é porque não é algo bom:
         * usaremos try/catch aqui. é possível que SHELL004 ainda não
         * esteja instalada. Exception é bem ampla, mas execute() não
         * lança SQLException. Vamos negociar assim, por enquanto.
         */
        try {
            lines = select.execute();
        } catch (Exception e) {
            return;
        }
        
        loaded = true;
        if (lines == null)
            return;
        
        tickets.clear();
        for (Object line : lines) {
            record = (Map<String, Object>)line;
            ticket = new AccessTicket();
            ticket.setAppname((String)record.get("APPNM"));
            ticket.setPagename((String)record.get("PAGEN"));
            ticket.setUsername((String)record.get("USRNM"));
            ticket.setSecret((String)record.get("SECRT"));
            ticket.setLocale((String)record.get("LOCAL"));
            tickets.put((String)record.get("TKTID"), ticket);
        }
    }
    
    public final void remove(String ticketcode, Function function) {
        tickets.remove(ticketcode);
        new Iocaste(function).update(QUERIES[DELETE], ticketcode);
    }
}
