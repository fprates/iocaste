package org.iocaste.internal;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    public String stylename;
    private Map<String, PageContext> pages;
    private String name;
    private Map<String, Map<String, String>> sheet;
    
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
     * @return
     */
    public final Map<String, Map<String, String>> getStyleSheet() {
        return sheet;
    }
    
    /**
     * 
     */
    public final void invalidateStyle() {
        if (sheet != null)
            sheet.clear();
        sheet = null;
    }
    
    /**
     * 
     * @param name
     * @param pagectx
     */
    public final void put(String name, PageContext pagectx) {
        pages.put(name, pagectx);
    }
    
    /**
     * 
     * @param sheet
     */
    public final void setStyleSheet(Map<String, Map<String, String>> sheet) {
        this.sheet = sheet;
    }
}
