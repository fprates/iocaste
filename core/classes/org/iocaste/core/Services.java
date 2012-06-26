package org.iocaste.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;

public class Services extends AbstractFunction {
    private static final int USERNAME_MAX_LEN = 12;
    private static final String CONFIG_FILE = "core.properties";
    private Map<String, UserContext> sessions;
    private DBServices db;
    private String host;
    private Properties properties;
    
    public Services() {
        sessions = new HashMap<String, UserContext>();
        db = new DBServices();

        export("checked_select", "checkedSelect");
        export("commit", "commit");
        export("create_user", "createUser");
        export("disconnect", "disconnect");
        export("get_context", "getContext");
        export("get_host", "getHost");
        export("get_locale", "getLocale");
        export("get_system_info", "getSystemInfo");
        export("get_system_parameter", "getSystemParameter");
        export("get_username", "getUsername");
        export("get_users", "getUsers");
        export("invalidate_auth_cache", "invalidateAuthCache");
        export("is_authorized", "isAuthorized");
        export("is_connected", "isConnected");
        export("login", "login");
        export("rollback", "rollback");
        export("select", "select");        
        export("set_context", "setContext");
        export("update", "update");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Object[] checkedSelect(Message message) throws Exception {
        Object[] results;
        Connection connection;
        String query, columns = message.getString("columns");
        String from = message.getString("from");
        String where = message.getString("where");
        Object[] criteria = message.get("criteria");
        StringBuilder sb = new StringBuilder("select ");
        
        sb.append((columns == null)? "*" : columns);
        
        if (from == null)
            throw new IocasteException("Table not specified.");
        
        sb.append(" from ").append(from);
        
        if (where != null)
            sb.append(" where ").append(where);
        
        query = sb.toString();
        connection = db.instance();
        
        results = db.select(connection, query, 0, criteria);
        connection.close();
        
        return results;
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void commit(Message message) throws Exception {
        Connection connection;
        String sessionid = message.getSessionid();
        UserContext context = sessions.get(sessionid);
        
        if (context == null)
            return;
        
        connection = context.getConnection();
        if (connection == null)
            return;
        
        db.commit(connection);
        connection.close();
        context.setConnection(null);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void createUser(Message message) throws Exception {
        User user = message.get("userdata");
        
        if (user.getUsername() == null || user.getSecret() == null)
            throw new Exception("Invalid username or password");
        
        db.update(getDBConnection(message.getSessionid()),
                "insert into users001(uname, secrt) values(?, ?)");
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
     * @throws Exception
     */
    private final Connection getDBConnection(String sessionid)
            throws Exception {
        UserContext context = sessions.get(sessionid);
        
        if (context.getConnection() == null)
            context.setConnection(db.instance());
        
        return context.getConnection();
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
    public final Locale getLocale(Message message) {
        UserContext context = sessions.get(message.getSessionid());
        
        return (context == null)? null : context.getLocale();
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Properties getSystemInfo(Message message)
            throws Exception {
        Properties dbprops = new Properties();
        Connection connection = getDBConnection(message.getSessionid());
        DatabaseMetaData metadata = connection.getMetaData();
        
        dbprops.put("db_product_name", metadata.getDatabaseProductName());
        dbprops.put("db_product_version", metadata.getDatabaseProductVersion());
        dbprops.put("jdbc_driver_name", metadata.getDriverName());
        dbprops.put("jdbc_driver_version", metadata.getDriverVersion());
        
        return dbprops;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String getSystemParameter(Message message) {
        String name = message.getString("parameter");
        
        return properties.getProperty(name);
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
                "select username, firstname, surname from User", 0);
        
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
     */
    public final void init() throws Exception {
        BufferedReader reader;
        FileInputStream fis;
        String path = new StringBuilder(System.getProperty("user.home")).
                append(System.getProperty("file.separator")).
                append(CONFIG_FILE).toString();
        File file = new File(path);
        
        if (!file.exists() || !file.isFile())
            throw new IocasteException("Iocaste not configured. " +
            		"Contact the administrator.");
        
        fis = new FileInputStream(file);
        reader = new BufferedReader(new InputStreamReader(fis));
        properties = new Properties();
        properties.load(reader);
        reader.close();
        fis.close();
        
        db.config(properties);
    }
    
    /**
     * 
     * @param message
     */
    public final void invalidateAuthCache(Message message) {
        AuthServices.invalidateCache();
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean isAuthorized(Message message) throws Exception {
        boolean fail;
        User user;
        String objvalue, usrvalue;
        Map<String, String> usrparameters, objparameters;
        Authorization[] usrauthorizations;
        Connection connection;
        Authorization objauthorization;
        UserContext context = sessions.get(message.getSessionid());
        
        if (context == null && isAuthorizedCall())
            return false;

        user = context.getUser();
        if (user == null)
            return false;
        
        objauthorization = message.get("authorization");
        connection = db.instance();
        
        usrauthorizations = AuthServices.getAuthorization(connection, db,
                user.getUsername(), objauthorization.getObject(),
                objauthorization.getAction());
        
        connection.close();
        
        if (usrauthorizations == null)
            return false;
        
        objparameters = objauthorization.getParameters();
        for (Authorization usrauthorization : usrauthorizations) {
            usrparameters = usrauthorization.getParameters();
            
            fail = false;
            for (String key : objparameters.keySet()) {
                objvalue = objparameters.get(key);
                if (objvalue == null || objvalue.length() == 0)
                    continue;
                
                usrvalue = usrparameters.get(key);
                if ((usrvalue != null) && (usrvalue.equals(objvalue)))
                    continue;
                
                fail = true;
                break;
            }
            
            if (!fail)
                return true;
        }
        
        return false;
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
     * @return
     */
    public final boolean isInitialized() {
        return (properties != null);
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
        Locale locale;
        String query;
        User user = null;
        String[] locale_ = message.getString("locale").split("_");
        String username = message.getString("user");
        String secret = message.getString("secret");
        String sessionid = message.getSessionid();
        
        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (username.length() > USERNAME_MAX_LEN)
            return false;

        connection = db.instance();
        
        query = "select uname, secrt from users001 where uname = ?";
        lines = db.select(connection, query, 1, username.toUpperCase());
        
        connection.close();
        
        if (lines == null)
            return false;
        
        columns = (Map<String, Object>)lines[0];
        user = getUserFromColumns(columns);
        user.setSecret((String)columns.get("SECRT"));
        
        if (!user.getSecret().equals(secret))
            return false;
        
        locale = (locale_.length == 1)?
                new Locale(locale_[0]) : new Locale(locale_[0], locale_[1]);
                
        context = new UserContext();
        context.setUser(user);
        context.setLocale(locale);
        
        sessions.put(sessionid, context);
        
        sessionid = sessionid.split(":")[0];
        if (sessions.containsKey(sessionid))
            return true;
        
        context = new UserContext();
        context.setUser(null);
        context.setLocale(locale);
        
        sessions.put(sessionid, context);
        
        return true;
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void rollback(Message message) throws Exception {
        Connection connection;
        String sessionid = message.getSessionid();
        UserContext context = sessions.get(sessionid);
        
        if (context == null && isAuthorizedCall())
            return;
        
        connection = context.getConnection();
        
        if (connection == null)
            return;
        
        db.rollback(connection);
        connection.close();
        context.setConnection(null);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception 
     */
    public final Object[] select(Message message) throws Exception {
        String query = message.getString("query");
        Object[] criteria = message.get("criteria");
        int rows = message.getInt("rows");
        
        Connection connection = getDBConnection(message.getSessionid());
        
        return db.select(connection, query, rows, criteria);
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
        String query = message.getString("query");
        Object[] criteria = message.get("criteria");
        Connection connection = getDBConnection(message.getSessionid());
    	
    	return db.update(connection, query, criteria);
    }

}
