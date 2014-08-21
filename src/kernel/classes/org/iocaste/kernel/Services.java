package org.iocaste.kernel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.kernel.common.AbstractFunction;
import org.iocaste.kernel.common.IocasteException;
import org.iocaste.kernel.common.Message;
import org.iocaste.kernel.common.user.Authorization;
import org.iocaste.kernel.common.user.User;
import org.iocaste.kernel.common.utils.ConversionResult;
import org.iocaste.kernel.common.utils.ConversionRules;

public class Services extends AbstractFunction {
    private static final int USERNAME_MAX_LEN = 12;
    private static final String IOCASTE_DIR = ".iocaste";
    private static final String CONFIG_FILE = "core.properties";
    private Map<String, UserContext> sessions;
    private Map<String, List<String>> usersessions;
    private DBServices db;
    private String host;
    private Properties properties;
    
    public Services() {
        sessions = new HashMap<>();
        usersessions = new HashMap<>();
        db = new DBServices();

        export("call_procedure", "callProcedure");
        export("checked_select", "checkedSelect");
        export("commit", "commit");
        export("conversion", "conversion");
        export("create_user", "createUser");
        export("disconnect", "disconnect");
        export("drop_user", "dropUser");
        export("get_context", "getContext");
        export("get_current_app", "getCurrentApp");
        export("get_host", "getHost");
        export("get_locale", "getLocale");
        export("get_session_info", "getSessionInfo");
        export("get_sessions", "getSessions");
        export("get_system_info", "getSystemInfo");
        export("get_system_parameter", "getSystemParameter");
        export("get_user_data", "getUserData");
        export("get_username", "getUsername");
        export("invalidate_auth_cache", "invalidateAuthCache");
        export("is_authorized", "isAuthorized");
        export("is_connected", "isConnected");
        export("is_initial_secret", "isInitialSecret");
        export("login", "login");
        export("rollback", "rollback");
        export("select", "select");        
        export("set_context", "setContext");
        export("set_current_app", "setCurrentApp");
        export("set_user_password", "setUserPassword");
        export("update", "update");
        export("update_user", "updateUser");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Object callProcedure(Message message) throws Exception {
        String sql = message.getString("sql");
        Map<String, Object> in = message.get("in");
        Map<String, Integer> out = message.get("out");
        String sessionid = message.getSessionid();
        Connection connection = getDBConnection(sessionid);
        
        return db.callProcedure(connection, sql, in, out);
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
        Map<String, String[]> ijoin = message.get("inner_join");
        StringBuilder sb = new StringBuilder("select ");
        
        sb.append((columns == null)? "*" : columns);
        if (from == null)
            throw new IocasteException("Table not specified.");
        
        sb.append(" from ").append(from);
        if (ijoin != null)
            for (String jointable : ijoin.keySet()) {
                sb.append(" inner join ").
                        append(jointable).
                        append(" on ");
                for (String clause : ijoin.get(jointable))
                    sb.append(clause);
            }
        
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
    
    public final ConversionResult conversion(Message message)
            throws Exception {
        String xml = message.getString("xml");
        ConversionRules data = message.get("data");
        
        return Convesion.execute(xml, data);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void createUser(Message message) throws Exception {
        String sessionid;
        User user = message.get("userdata");
        
        if (user.getUsername() == null || user.getSecret() == null)
            throw new IocasteException("Invalid username or password");
        
        sessionid = message.getSessionid();
        UserServices.add(user, getDBConnection(sessionid), db);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void disconnect(Message message) throws Exception {
        UserContext context;
        String sessionid = message.getSessionid();

        if (sessionid == null)
            throw new IocasteException("Null session not allowed.");
        
        if (!sessions.containsKey(sessionid))
            return;
        
        context = sessions.get(sessionid);
        usersessions.remove(context.getUser().getUsername());
        sessions.remove(sessionid);
    }
    
    /**
     * 
     * @param message
     */
    public final void dropUser(Message message) throws Exception {
        String sessionid = message.getSessionid();
        String username = message.getString("username");
        
        UserServices.drop(username, getDBConnection(sessionid), db);
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
     */
    public final String getCurrentApp(Message message) {
        String sessionid = message.getSessionid();
        String appname = sessions.get(sessionid).getCurrentApp();
        
        return appname;
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
    public final Map<String, Object> getSessionInfo(Message message)
            throws Exception {
        String sessionid = message.getString("sessionid");
        Connection connection = getDBConnection(message.getSessionid());
        
        return UserServices.getSessionInfo(sessions.get(sessionid),
                connection, db);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception 
     */
    public final String[] getSessions(Message message) throws Exception {
        UserContext context;
        User user;
        Set<String> users;
        
        users = new TreeSet<>();
        for (String sessionid : sessions.keySet()) {
            context = sessions.get(sessionid);
            user = context.getUser();
            if (user == null)
                continue;
            
            users.add(sessionid);
        }
        
        return users.toArray(new String[0]);
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
            throw new IocasteException("Null session not allowed.");
        
        if (!sessions.containsKey(sessionid))
            return null;
        
        return sessions.get(sessionid).getUser().getUsername();
    }
    
    public final User getUserData(Message message) throws Exception {
        String username = message.getString("username");
        User user = UserServices.getUserData(db, username);
        user.setSecret(null);
        return user;
    }
    
    /**
     * 
     */
    public final void init() throws Exception {
        BufferedReader reader;
        FileInputStream fis;
        String path = new StringBuilder(System.getProperty("user.home")).
                append(File.separator).append(IOCASTE_DIR).
                append(File.separator).append(CONFIG_FILE).toString();
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
        for (String sessionid : sessions.keySet())
            sessions.get(sessionid).setAuthorizations(null);
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
        usrauthorizations = context.getAuthorizations();
        if (usrauthorizations == null) {
            connection = db.instance();
            usrauthorizations = AuthServices.getAuthorizations(
                    connection, db, user.getUsername());
            connection.close();
            context.setAuthorizations(usrauthorizations);
        }
        
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
     */
    public final boolean isInitialSecret(Message message) {
        String sessionid = message.getSessionid();
        UserContext context = sessions.get(sessionid);
        
        return context.getUser().isInitialSecret();
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean login(Message message) throws Exception {
        UserContext context;
        Locale locale;
        int terminal;
        List<String> sessionslist;
        User user;
        String[] composed, locale_ = message.getString("locale").split("_");
        String username = message.getString("user");
        String secret = message.getString("secret");
        String sessionid = message.getSessionid();
        
        if (sessionid == null)
            throw new Exception("Null session not allowed.");
        
        if (username.length() > USERNAME_MAX_LEN)
            return false;

        user = UserServices.getUserData(db, username);
        if ((user == null) || (!user.getSecret().equals(secret)))
            return false;
        
        if (locale_.length == 1)
            locale = new Locale(locale_[0]);
        else
            locale = new Locale(locale_[0], locale_[1]);
                
        context = new UserContext(locale);
        context.setUser(user);
        sessions.put(sessionid, context);
        if (usersessions.containsKey(username)) {
            sessionslist = usersessions.get(username);
        } else {
            sessionslist = new ArrayList<>();
            usersessions.put(username, sessionslist);
        }
        
        sessionslist.add(sessionid);
        composed = sessionid.split(":");
        sessionid = composed[0];
        terminal = Integer.parseInt(composed[1]);
        context.setTerminal(terminal);
        if (sessions.containsKey(sessionid))
            return true;
        
        context = new UserContext(locale);
        context.setUser(null);
        context.setTerminal(terminal);
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
        int rows = message.geti("rows");
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
     * @param message
     */
    public final void setCurrentApp(Message message) {
        String sessionid = message.getSessionid();
        String currentapp = message.getString("current_app");
        
        sessions.get(sessionid).setCurrentApp(currentapp);
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
     * @throws Exception
     */
    public final void setUserPassword(Message message) throws Exception {
        String sessionid = message.getSessionid();
        String secret = message.getString("secret");
        User user = sessions.get(sessionid).getUser();
        
        user.setSecret(secret);
        user.setInitialSecret(false);
        
        updateUser(user, sessionid);
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

    /**
     * 
     * @param user
     * @param sessionid
     * @throws Exception
     */
    private final void updateUser(User user, String sessionid)
            throws Exception {
        UserServices.update(user, getDBConnection(sessionid), db);
        List<String> sids = usersessions.get(user.getUsername());
        
        if (sids== null)
            return;
        
        for (String sid : sids)
            sessions.get(sid).setAuthorizations(null);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void updateUser(Message message) throws Exception {
        User user = message.get("user");
        String sessionid = message.getSessionid();
        
        updateUser(user, sessionid);
    }
}
