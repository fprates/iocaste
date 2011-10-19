package org.iocaste.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.HibernateUtil;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class Login extends AbstractFunction {
    private static final int USERNAME_MAX_LEN = 12;
    private Map<String, Context> sessions;
    
    public Login() {
        sessions = new HashMap<String, Context>();
        
        export("login", "login");
        export("is_connected", "isConnected");
        export("get_username", "getUsername");
        export("create_user", "createUser");
        export("disconnect", "disconnect");
        export("get_context", "getContext");
        export("set_context", "setContext");
        export("get_users", "getUsers");
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
    
    /**
     * 
     * @param message
     * @throws Exception
     */
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
     * @throws Exception
     */
    public final Object getContext(Message message) throws Exception {
        String sessionid = message.getSessionid();
        String contextid = message.getString("context_id");
        
        return sessions.get(sessionid).getObject(contextid);
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
        
        return sessions.get(sessionid).getUser().getUsername();
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final User[] getUsers(Message message) {
        Object[] fields;
        User[] users;
        User user;
        int t;
        Session session = HibernateUtil.getSessionFactory().
                getCurrentSession();
        List<?> list = session.createQuery(
                "select username, firstname, surname from User").list();
        
        t = list.size();
        if (t == 0)
            return null;
        
        users = new User[t];
        t = 0;
        for (Object object : list) {
            fields = (Object[])object;
            user = new User();
            user.setUsername((String)fields[0]);
            user.setFirstname((String)fields[1]);
            user.setSurname((String)fields[2]);
            users[t] = user;
            t++;
        }
        
        return users; 
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
    public final boolean login(Message message) throws Exception {
        Context context;
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
        
        context = new Context();
        context.setUser(user_);
        
        sessions.put(sessionid, context);
        
        return true;
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void setContext(Message message) throws Exception {
        String sessionid = message.getSessionid();
        String contextid = message.getString("context_id");
        
        sessions.get(sessionid).setObject(contextid, message.get("object"));
    }

}
