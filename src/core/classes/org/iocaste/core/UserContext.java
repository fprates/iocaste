package org.iocaste.core;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.iocaste.protocol.user.User;

public class UserContext {
    private Map<String, Object> objects;
    private User user;
    private Connection connection;
    private Locale locale;
    private int terminal;
    private Date conndate;
    private String currentapp;
    
    public UserContext(Locale locale) {
        objects = new HashMap<String, Object>();
        this.locale = locale;
        conndate = Calendar.getInstance().getTime();
    }
    
    /**
     * 
     * @return
     */
    public final String getCurrentApp() {
        return currentapp;
    }
    
    /**
     * 
     * @return
     */
    public final Date getConnTime() {
        return conndate;
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
    public final int getTerminal() {
        return terminal;
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
     * @param currentapp
     */
    public final void setCurrentApp(String currentapp) {
        this.currentapp = currentapp;
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
     * @param terminal
     */
    public final void setTerminal(int terminal) {
        this.terminal = terminal;
    }
    
    /**
     * 
     * @param user
     */
    public final void setUser(User user) {
        this.user = user;
    }
}
