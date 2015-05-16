package org.iocaste.appbuilder.common.panel;

import java.util.LinkedHashMap;
import java.util.Map;

public class PanelPageItemContext {
    public Map<String, PanelPageItemContextEntry> entries;
    
    public PanelPageItemContext() {
        entries = new LinkedHashMap<>();
    }
    
    private final void call(
            String group, String text, String task, PanelPageEntryType type) {
        PanelPageItemContextEntry entry;
        
        entry = new PanelPageItemContextEntry();
        entry.group = group;
        entry.type = type;
        entry.task = task;
        entries.put(text, entry);
    }
    
    public final void call(String text, String task) {
        call(null, text, task, PanelPageEntryType.TASK);
    }
    
    public final void callgroup(String group, String text, String task) {
        call(group, text, task, PanelPageEntryType.TASK);
    }
    
    public final void group(String group, String text) {
        call(group, text, null, PanelPageEntryType.GROUP);
    }
}