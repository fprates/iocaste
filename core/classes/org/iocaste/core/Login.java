package org.iocaste.core;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Login extends AbstractFunction {
    private static final int USERNAME_MAX_LEN = 12;
    private Map<String, User> sessions;
    
    public Login() {
        sessions = new HashMap<String, User>();
        
        export("login", "login");
        export("is_connected", "isConnected");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean login(Message message) throws Exception {
        String user = message.getString("user");
        String secret = message.getString("secret");
        String sessionid = message.getSessionid();
        
        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (user.length() > USERNAME_MAX_LEN)
            return false;
            
        User user_ = (User)load(User.class, user.toUpperCase());
        
        if (user_ == null)
            return false;
        
        user_.setSecret(user_.getSecret().trim());
        
        if (!user_.getSecret().equals(secret))
            return false;
        
        sessions.put(sessionid, user_);
        
        return true;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final boolean isConnected(Message message) {
        String sessionid = message.getSessionid();
        
        if (sessionid == null)
            return false;
        
        return sessions.containsKey(sessionid);
    }

}
