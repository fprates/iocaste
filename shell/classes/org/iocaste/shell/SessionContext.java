package org.iocaste.shell;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SessionContext {
    private Map<String, AppContext> apps;
    private Stack<String[]> pagestack;

    public SessionContext() {
        apps = new HashMap<String, AppContext>();
        pagestack = new Stack<String[]>();
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
     * @return
     */
    public final String[] home() {
        String[] home = null;
        
        while (pagestack.size() > 1)
            home = pagestack.pop();
        
        return home;
    }
    
    /**
     * 
     * @return
     */
    public final String[] popPage() {
        return pagestack.pop();
    }
    
    /**
     * 
     * @param name
     */
    public final void pushPage(String appname, String pagename) {
        String[] entry = new String[2];
        
        entry[0] = appname;
        entry[1] = pagename;
        
        pagestack.push(entry);
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