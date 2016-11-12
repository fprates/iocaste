package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

public class PageContext {
    private Map<String, ContextEntry> tools;
    
    public PageContext() {
        tools = new HashMap<>();
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
    
    public final void put(String name, ContextEntry entry) {
        tools.put(name, entry);
    }
    
    public final void putAll(PageContext pagectx) {
        tools.putAll(pagectx.getTools());
    }
}