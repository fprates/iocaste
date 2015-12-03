package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.texteditor.common.TextEditor;

public class ViewComponents {
    public Map<String, ComponentEntry> entries;
    public Map<String, DashboardFactory> dashboards;
    public Map<String, DashboardComponent> dashboardgroups;
    public Map<String, TextEditor> editors;
    
    public ViewComponents() {
        entries = new LinkedHashMap<>();
        dashboards = new HashMap<>();
        dashboardgroups = new HashMap<>();
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
        return (T)entries.get(name).data;
    }
    
    public final void reset() {
        entries.clear();
        dashboards.clear();
        dashboardgroups.clear();
        editors.clear();
    }
}

