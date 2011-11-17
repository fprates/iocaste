package org.iocaste.shell;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class AppContext {
    private Map<String, PageContext> pages;
    private String name;
    private Stack<String> pagestack;
    
    public AppContext(String name) {
        this.name = name;
        pages = new HashMap<String, PageContext>();
        pagestack = new Stack<String>();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final boolean contains(String name) {
        return pages.containsKey(name);
    }
    
    /**
     * 
     * @return
     */
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final PageContext getPageContext(String name) {
        return pages.get(name);
    }
    
    /**
     * 
     * @param name
     */
    public final void pushPage(String name) {
        pagestack.push(name);
    }
    
    /**
     * 
     * @return
     */
    public final String popPage() {
        return pagestack.pop();
    }
    
    /**
     * 
     * @param name
     * @param pagectx
     */
    public final void put(String name, PageContext pagectx) {
        pages.put(name, pagectx);
    }
}
