package org.iocaste.core;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class Services extends AbstractFunction {
    private static final int USERNAME_MAX_LEN = 12;
    private Map<String, UserContext> sessions;
    private DBServices db;
    private String host;
    private Map<String, String> properties;
    
    public Services() {
        sessions = new HashMap<String, UserContext>();
        properties = new HashMap<String, String>();
        db = new DBServices();
        
        properties.put("db.user", "iocastedb");
        
        export("login", "login");
        export("is_connected", "isConnected");
        export("get_username", "getUsername");
        export("create_user", "createUser");
        export("disconnect", "disconnect");
        export("get_context", "getContext");
        export("set_context", "setContext");
        export("get_users", "getUsers");
        export("select", "select");
        export("update", "update");
        export("commit", "commit");
        export("checked_select", "checkedSelect");
        export("get_host", "getHost");
        export("get_system_parameter", "getSystemParameter");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Object[] checkedSelect(Message message) throws Exception {
        Object[] results;
        String columns = message.getString("columns");
        String from = message.getString("from");
        String where = message.getString("where");
        Object[] criteria = (Object[])message.get("criteria");
        String query = new StringBuilder("select").append(columns).
                append(" from ").append(from).append(" where ").append(where).
                toString();
        Connection connection = db.instance();
        
        results = db.select(connection, query, criteria);
        connection.close();
        
        return results;
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void commit(Message message) throws Exception {
        String sessionid = message.getSessionid();
        db.commit(getDBConnection(sessionid));
    }
    
    /**
     * 
     * @param message
     */
    public final void createUser(Message message) throws Exception {
        User user = (User)message.get("userdata");
        
        if (user.getUsername() == null || user.getSecret() == null)
            throw new Exception("Invalid username or password");
        
        db.update(getDBConnection(message.getSessionid()),
                "insert into users001(uname, secrt) values(?, ?)", null);
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
     * @param sessionid
     * @return
     */
    private final Connection getDBConnection(String sessionid) {
        return sessions.get(sessionid).getConnection();
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String getHost(Message message) {
        return host;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String getSystemParameter(Message message) {
        String name = message.getString("parameter");
        
        return properties.get(name);
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
     * @param columns
     * @return
     */
    private final User getUserFromColumns(Map<String, Object> columns) {
        User user = new User();
        user.setUsername((String)columns.get("UNAME"));
        user.setFirstname((String)columns.get("FNAME"));
        user.setSurname((String)columns.get("SNAME"));
        
        return user;
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public final User[] getUsers(Message message) throws Exception {
        Map<String, Object> columns;
        User[] users;
        int t;
        Object[] lines = db.select(getDBConnection(message.getSessionid()),
                "select username, firstname, surname from User", null);
        
        t = lines.length;
        if (t == 0)
            return null;
        
        users = new User[t];
        t = 0;
        
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            users[t++] = getUserFromColumns(columns);
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
    @SuppressWarnings("unchecked")
    public final boolean login(Message message) throws Exception {
        UserContext context;
        Object[] lines;
        Map<String, Object> columns;
        Connection connection;
        User user = null;
        String username = message.getString("user");
        String secret = message.getString("secret");
        String sessionid = message.getSessionid();
        
        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (username.length() > USERNAME_MAX_LEN)
            return false;

        connection = db.instance();
        
        lines = db.select(connection,
                "select uname, secrt from users001 where uname = ?",
                new Object[] {username.toUpperCase()});
        
        connection.close();
        
        if (lines.length == 0)
            return false;
        
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            user = getUserFromColumns(columns);
            user.setSecret((String)columns.get("SECRT"));
            break;
        }
        
        if (!user.getSecret().equals(secret))
            return false;
        
        context = new UserContext();
        context.setUser(user);
        context.setConnection(db.instance());
        
        sessions.put(sessionid, context);
        
        return true;
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception 
     */
    public final Object[] select(Message message) throws Exception {
        UserContext context = sessions.get(message.getSessionid());
        
        return db.select(context.getConnection(), message.getString("query"),
                (Object[])message.get("criteria"));
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
    
    /**
     * 
     * @param host
     */
    public final void setHost(String host) {
        this.host = host;
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int update(Message message) throws Exception {
    	UserContext context = sessions.get(message.getSessionid());
    	
    	return db.update(context.getConnection(), message.getString("query"),
    	        (Object[])message.get("criteria"));
    }

}
