package org.iocaste.shell;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SessionContext {
    private Map<String, AppContext> apps;
    private Stack<String[]> pagestack;

    public SessionContext() {
        apps = new HashMap<>();
        pagestack = new Stack<>();
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
    public final String[][] getPagesNames() {
        return pagestack.toArray(new String[0][0]);
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
    
    /**
     * 
     * @param app
     * @param page
     */
    public final void setPagesPosition(String app, String page) {
        String[] entry;
        
        do {
            entry = pagestack.pop();
        } while (!entry[0].equals(app) || !entry[1].equals(page));
    }
}