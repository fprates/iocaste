package org.iocaste.kernel.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.common.AbstractFunction;
import org.iocaste.kernel.database.Database;
import org.iocaste.kernel.users.Users;

public class Session extends AbstractFunction {
    public Map<String, UserContext> sessions;
    public Map<String, List<String>> usersessions;
    public Database database;
    public Users users;
    
    public Session() {
        sessions = new HashMap<>();
        usersessions = new HashMap<>();
        export("disconnect", new Disconnect());
        export("get_current_app", new GetCurrentApp());
        export("get_locale", new GetLocale());
        export("get_session_info", new GetSessionInfo());
        export("get_sessions", new GetSessions());
        export("get_username", new GetUsername());
        export("invalidate_auth_cache", new InvalidateAuthCache());
        export("is_connected", new IsConnected());
        export("login", new Login());
        export("set_current_app", new SetCurrentApp());
    }
}