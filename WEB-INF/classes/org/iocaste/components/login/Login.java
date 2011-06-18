package org.iocaste.components.login;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Login extends AbstractFunction {
    private Map<String, User> sessions;
    
    public Login() {
        sessions = new HashMap<String, User>();
        
        export("login");
        export("is_connected");
    }
    
    private final boolean login(String user, String secret, String sessionid) {
        User user_ = (User)load(User.class, user.toUpperCase());
        
        if (user_ == null)
            return false;
        
        user_.setSecret(user_.getSecret().trim());
        
        if (!user_.getSecret().equals(secret))
            return false;
        
        sessions.put(sessionid, user_);
        
        return true;
    }
    
    private final boolean isConnected(String sessionid) {
        return sessions.containsKey(sessionid);
    }
    
    @Override
    public Object run(Message message) {
        String id = message.getId();
        
        if (id.equals("login"))
            return login(message.getString("user"),
                    message.getString("secret"), message.getSessionid());
        
        if (id.equals("is_connected"))
            return isConnected(message.getSessionid());
        
        return null;
    }

}
