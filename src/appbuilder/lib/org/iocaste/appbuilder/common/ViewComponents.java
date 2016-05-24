package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.texteditor.common.TextEditor;

public class ViewComponents {
    public Map<String, ComponentEntry> entries;
    public Map<String, TextEditor> editors;
    
    public ViewComponents() {
        entries = new LinkedHashMap<>();
        editors = new HashMap<>();
    }
    
    public final void add(AbstractComponentData data) {
        ComponentEntry entry = new ComponentEntry();
        entry.data = data;
        entries.put(data.name, entry);
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends AbstractComponentTool> T getComponent(String name) {
        return (T)entries.get(name).component;
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends AbstractComponentData> T getComponentData(
            String name) {
        ComponentEntry entry = entries.get(name);
        return (entry == null)? null : (T)entry.data;
    }
    
    public final void reset() {
        entries.clear();
        editors.clear();
    }
}

