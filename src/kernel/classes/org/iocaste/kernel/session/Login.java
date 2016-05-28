package org.iocaste.kernel.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.users.GetUserData;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class Login extends AbstractHandler {
    private static final int USERNAME_MAX_LEN = 12;
    
    @Override
    public Object run(Message message) throws Exception {
        UserContext context;
        Locale locale;
        int terminal;
        List<String> sessionslist;
        User user;
        GetUserData getuserdata;
        Session session = getFunction();
        String[] composed, locale_ = message.getst("locale").split("_");
        String username = message.getst("user");
        String secret = message.getst("secret");
        String sessionid = message.getSessionid();
        
        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (username.length() > USERNAME_MAX_LEN)
            return false;

        getuserdata = session.users.get("get_user_data");
        user = getuserdata.run(username);
        if ((user == null) || (!user.getSecret().equals(secret)))
            return false;
        
        if (locale_.length == 1)
            locale = new Locale(locale_[0]);
        else
            locale = new Locale(locale_[0], locale_[1]);
                
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
            return true;
        
        context = new UserContext(locale);
        context.setUser(null);
        context.setTerminal(terminal);
        session.sessions.put(sessionid, context);
        return true;
    }

}
