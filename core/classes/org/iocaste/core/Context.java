package org.iocaste.core;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.user.User;

public class Context {
    private Map<String, Object> objects;
    private User user;
    
    public Context() {
        objects = new HashMap<String, Object>();
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
