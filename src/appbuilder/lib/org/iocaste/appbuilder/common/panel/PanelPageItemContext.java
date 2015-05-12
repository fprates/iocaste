package org.iocaste.appbuilder.common.panel;

import java.util.LinkedHashMap;
import java.util.Map;

public class PanelPageItemContext {
    public Map<String, PanelPageItemContextEntry> entries;
    
    public PanelPageItemContext() {
        entries = new LinkedHashMap<>();
    }
    
    public final void call(String text, String task) {
        PanelPageItemContextEntry entry;
        
        entry = new PanelPageItemContextEntry();
        entry.type = PanelPageEntryType.TASK;
        entry.task = task;
        entries.put(text, entry);
    }
}