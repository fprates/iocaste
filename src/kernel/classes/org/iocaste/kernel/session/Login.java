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
        UserContext context;
        String[] composed;
        List<String> sessionslist;
        
        context = new UserContext(locale);
        context.setUser(user);
        session.sessions.put(sessionid, context);
        if (session.usersessions.containsKey(username)) {
            sessionslist = session.usersessions.get(username);
        } else {
            sessionslist = new ArrayList<>();
            session.usersessions.put(username, sessionslist);
        }
        
        sessionslist.add(sessionid);
        composed = sessionid.split(":");
        sessionid = composed[0];
        terminal = Integer.parseInt(composed[1]);
        context.setTerminal(terminal);
        if (session.sessions.containsKey(sessionid))
            return;
        
        context = new UserContext(locale);
        context.setUser(null);
        context.setTerminal(terminal);
        session.sessions.put(sessionid, context);
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Locale locale;
        User user;
        GetUserData getuserdata;
        Session session;
        Connection connection;
        String[] locale_ = message.getst("locale").split("_");
        String username = message.getst("user");
        String secret = message.getst("secret");
        String sessionid = message.getSessionid();
        
        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (username.length() > USERNAME_MAX_LEN)
            return false;

        session = getFunction();
        connection = session.database.instance();
        try {
            getuserdata = session.users.get("get_user_data");
            user = getuserdata.run(session.users, connection, username);
            if ((user == null) || !isSecretOk(
                    connection, getuserdata, user, secret))
                return false;
            
            if (locale_.length == 1)
                locale = new Locale(locale_[0]);
            else
                locale = new Locale(locale_[0], locale_[1]);
            
            instance(session, user, sessionid, username, locale);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            session.database.free(connection);
        }
        return true;
    }
    
    public final String run(String sessionid, String dbname) {
        String dbsessionid = UUID.randomUUID().toString();
        Session session = getFunction();
        UserContext userctx = session.sessions.get(sessionid);
        
        instance(session, userctx.getUser(), dbsessionid, null, null);
        
        return dbsessionid;
    }

}
