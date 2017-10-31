package org.iocaste.kernel.session;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.users.GetUserData;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class Login extends AbstractHandler {
    private static final int USERNAME_MAX_LEN = 12;
    
    private final boolean isSecretOk(Connection connection,
            GetUserData getuserdata, User user, String secret) throws Exception
    {
        return (getuserdata.getSecret(connection, user).equals(secret));
    }
    
    private final void instance(Session session,
            User user, String sessionid, String username, Locale locale) {
        int terminal;
        String[] composed;
        List<String> sessionslist;
        SessionContext sessionctx;
        
        if ((sessionctx = session.sessions.get(sessionid)) == null)
            session.sessions.put(sessionid, sessionctx = new SessionContext());
        sessionctx.usercontext = new UserContext(locale);
        sessionctx.usercontext.setUser(user);
        
        if (session.usersessions.containsKey(username)) {
            sessionslist = session.usersessions.get(username);
        } else {
            sessionslist = new ArrayList<>();
            session.usersessions.put(username, sessionslist);
        }
        
        sessionslist.add(sessionid);
        composed = sessionid.split(":");
        sessionid = composed[0];
        terminal = (composed.length > 1)? Integer.parseInt(composed[1]) : 0;
        sessionctx.usercontext.setTerminal(terminal);
        
        if (session.sessions.containsKey(sessionid))
            return;
        
        sessionctx = new SessionContext();
        sessionctx.usercontext = new UserContext(locale);
        sessionctx.usercontext.setUser(null);
        sessionctx.usercontext.setTerminal(terminal);
        session.sessions.put(sessionid, sessionctx);
    }
    
    @Override
    public Object run(Message message) throws Exception {
        String locale;
        String username = message.getst("user");
        String secret = message.getst("secret");
        String sessionid = message.getSessionid();
        
        if ((locale = message.getst("locale")) == null)
            throw new Exception("login locale undefined.");
        return run(sessionid, username, secret, locale);
    }
    
    public final boolean run(
            String sessionid, String username, String secret, String _locale)
                    throws Exception {
        Locale locale;
        User user;
        GetUserData getuserdata;
        Session session;
        Connection connection;
        String[] localetokens;
        
        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (username.length() > USERNAME_MAX_LEN)
            return false;

        localetokens = _locale.split("_");
        session = getFunction();
        connection = session.database.getDBConnection(sessionid);
        try {
            getuserdata = session.users.get("get_user_data");
            user = getuserdata.run(session.users, connection, username);
            if ((user == null) || !isSecretOk(
                    connection, getuserdata, user, secret))
                return false;
            
            if (localetokens.length == 1)
                locale = new Locale(localetokens[0]);
            else
                locale = new Locale(localetokens[0], localetokens[1]);
            
            instance(session, user, sessionid, username, locale);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            session.database.freeConnection(sessionid);
        }
        return true;
    }
    
    public final String run(String sessionid, String dbname) {
        String dbsessionid = UUID.randomUUID().toString();
        Session session = getFunction();
        UserContext userctx = session.sessions.get(sessionid).usercontext;
        
        instance(session, userctx.getUser(), dbsessionid, null, null);
        
        return dbsessionid;
    }

}
