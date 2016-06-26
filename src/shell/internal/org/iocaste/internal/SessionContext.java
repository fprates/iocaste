package org.iocaste.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.View;

public class SessionContext {
    public String loginapp;
    private Map<String, AppContext> apps;
    private Stack<PageStackItem> pagestack;

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
    public final PageStackItem[] getPagesNames() {
        return pagestack.toArray(new PageStackItem[0]);
    }
    
    /**
     * 
     * @return
     */
    public final PageStackItem home(String page) {
        PageStackItem home = null;
        
        while (pagestack.size() > 1) {
            home = pagestack.pop();
            if ((page != null) && home.getPage().equals(page))
                break;
        }
        
        return home;
    }
    
    /**
     * 
     * @return
     */
    public final PageStackItem popPage() {
        return (pagestack.size() > 1)? pagestack.pop() : null;
    }
    
    /**
     * 
     * @param name
     */
    public final void pushPage(View view) {
        String appname = view.getAppName();
        String pagename = view.getPageName();
        PageStackItem entry = new PageStackItem(appname, pagename);
        
        entry.setTitle(view.getTitle());
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
        PageStackItem entry;
        
        do {
            entry = pagestack.pop();
        } while (!entry.getApp().equals(app) || !entry.getPage().equals(page));
    }
}