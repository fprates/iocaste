package org.iocaste.internal;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    private Map<String, PageContext> pages;
    private String name;
    public String mode;
    
    public AppContext(String name) {
        this.name = name;
        pages = new HashMap<>();
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
     * @param pagectx
     */
    public final void put(String name, PageContext pagectx) {
        pages.put(name, pagectx);
    }
}
