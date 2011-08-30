package org.iocaste.core;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.HibernateUtil;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class Login extends AbstractFunction {
    private static final int USERNAME_MAX_LEN = 12;
    private Map<String, User> sessions;
    
    public Login() {
        sessions = new HashMap<String, User>();
        
        export("login", "login");
        export("is_connected", "isConnected");
        export("get_username", "getUsername");
        export("create_user", "createUser");
        export("disconnect", "disconnect");
    }
    
    /**
     * 
     * @param message
     */
    public final void createUser(Message message) throws Exception {
        User user = (User)message.get("userdata");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        if (user.getUsername() == null || user.getSecret() == null)
            throw new Exception("Invalid username or password");
        
        user.setUsername(user.getUsername().toUpperCase());
        
        session.save(user);
    }
    
    public final void disconnect(Message message) throws Exception {
        String sessionid = message.getSessionid();

        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (sessions.containsKey(sessionid))
            sessions.remove(sessionid);
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
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final String getUsername(Message message) throws Exception {
        String sessionid = message.getSessionid();

        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (!sessions.containsKey(sessionid))
            return null;
        
        return sessions.get(sessionid).getUsername();
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
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (user.length() > USERNAME_MAX_LEN)
            return false;
            
        User user_ = (User)session.get(User.class, user.toUpperCase());
        
        if (user_ == null)
            return false;
        
        if (!user_.getSecret().equals(secret))
            return false;
        
        sessions.put(sessionid, user_);
        
        return true;
    }

}
