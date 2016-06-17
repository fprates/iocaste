package org.iocaste.kernel.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.database.Database;
import org.iocaste.kernel.users.Users;
import org.iocaste.protocol.AbstractFunction;

public class Session extends AbstractFunction {
    public Map<String, UserContext> sessions;
    public Map<String, List<String>> usersessions;
    public Database database;
    public Users users;
    public Map<Byte, List<EventHandler>> events;
    
    public Session() {
        sessions = new HashMap<>();
        usersessions = new HashMap<>();
        events = new HashMap<>();
        export("disconnect", new Disconnect());
        export("get_current_app", new GetCurrentApp());
        export("get_locale", new GetLocale());
        export("get_session_info", new GetSessionInfo());
        export("get_sessions", new GetSessions());
        export("get_username", new GetUsername());
        export("handler_add", new HandlerAdd());
        export("invalidate_auth_cache", new InvalidateAuthCache());
        export("is_connected", new IsConnected());
        export("login", new Login());
        export("set_current_app", new SetCurrentApp());
    }
    
    public void add(byte code, EventHandler handler) {
        List<EventHandler> handlers = events.get(code);
        
        if (handlers == null) {
            handlers = new ArrayList<>();
            events.put(code, handlers);
        }
        handlers.add(handler);
    }
}
