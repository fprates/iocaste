package org.iocaste.shell;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    private Map<String, PageContext> pages;

    public AppContext() {
        pages = new HashMap<String, PageContext>();
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
