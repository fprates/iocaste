package org.iocaste.core;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.iocaste.protocol.user.User;

public class UserContext {
    private Map<String, Object> objects;
    private User user;
    private Connection connection;
    private Locale locale;
    
    public UserContext() {
        objects = new HashMap<String, Object>();
    }
    
    /**
     * 
     * @return
     */
    public final Connection getConnection() {
        return connection;
    }
    
    /**
     * 
     * @return
     */
    public final Locale getLocale() {
        return locale;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final Object getObject(String name) {
        return objects.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final User getUser() {
        return user;
    }
    
    /**
     * 
     * @param connection
     */
    public final void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * 
     * @param locale
     */
    public final void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    /**
     * 
     * @param name
     * @param object
     */
    public final void setObject(String name, Object object) {
        if (objects.containsKey(name))
            objects.remove(name);
        
        objects.put(name, object);
    }
    
    /**
     * 
     * @param user
     */
    public final void setUser(User user) {
        this.user = user;
    }
}
