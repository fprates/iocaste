package org.iocaste.shell;

import java.util.HashMap;
import java.util.Map;

public class SessionContext {
    private Map<String, AppContext> apps;

    public SessionContext() {
        apps = new HashMap<String, AppContext>();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final boolean contains(String name) {
        return apps.containsKey(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final AppContext getAppContext(String name) {
        return apps.get(name);
    }
    
    /**
     * 
     * @param name
     * @param appctx
     */
    public final void put(String name, AppContext appctx) {
        apps.put(name, appctx);
    }
}
