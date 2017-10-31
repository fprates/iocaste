package org.iocaste.kernel.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.kernel.database.Database;
import org.iocaste.kernel.session.tickets.AddTicket;
import org.iocaste.kernel.session.tickets.RemoveTicket;
import org.iocaste.kernel.session.tickets.TicketLogin;
import org.iocaste.kernel.users.Users;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.runtime.common.AccessTicket;

public class Session extends AbstractFunction {
    public final static byte INSERT = 0;
    public final static byte DELETE = 1;
    public final static String[] QUERIES = {
        "insert into SHELL004(TKTID, APPNM, PAGEN, USRNM, SECRT, LOCAL)"
            + " values (?, ?, ?, ?, ?, ?)",
        "delete from SHELL004 where TKTID = ?"
    };
    public Map<String, SessionContext> sessions;
    public Map<String, List<String>> usersessions;
    public Map<String, AccessTicket> tickets;
    public Database database;
    public Users users;
    
    public Session() {
        sessions = new HashMap<>();
        usersessions = new HashMap<>();
        tickets = new HashMap<>();
        
        export("context_new", new NewContext());
        export("disconnect", new Disconnect());
        export("get_current_app", new GetCurrentApp());
        export("get_locale", new GetLocale());
        export("get_session_info", new GetSessionInfo());
        export("get_sessions", new GetSessions());
        export("get_username", new GetUsername());
        export("invalidate_auth_cache", new InvalidateAuthCache());
        export("is_connected", new IsValidContext());
        export("login", new Login());
        export("set_current_app", new SetCurrentApp());
        export("ticket_add", new AddTicket());
        export("ticket_remove", new RemoveTicket());
        export("ticket_login", new TicketLogin());
    }
}
