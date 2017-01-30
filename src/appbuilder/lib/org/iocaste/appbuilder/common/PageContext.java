package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

public class PageContext {
    private Map<String, ContextEntry> tools;
    private Map<String, String> parents;
    
    public PageContext() {
        tools = new HashMap<>();
        parents = new HashMap<>();
    }
    
    public final boolean contains(String name) {
        return tools.containsKey(name);
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends ContextEntry> T get(String name) {
        return (T)tools.get(name);
    }
    
    public final Map<String, ContextEntry> getTools() {
        return tools;
    }
    
    public final String parentget(String name) {
        return parents.get(name);
    }
    
    public final void parentput(String parent, String name) {
        parents.put(name, parent);
    }
    
    public final void put(String name, ContextEntry entry) {
        tools.put(name, entry);
    }
    
    public final void putAll(PageContext pagectx) {
        tools.putAll(pagectx.getTools());
    }
}