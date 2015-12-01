package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.reporttool.ReportToolEntry;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolEntry;
import org.iocaste.texteditor.common.TextEditor;

public class ViewComponents {
    public Map<ViewSpecItem.TYPES, Map<String, ComponentEntry>> entries;
    public Map<String, TableToolEntry> tabletools;
    public Map<String, DashboardFactory> dashboards;
    public Map<String, DashboardComponent> dashboardgroups;
    public Map<String, ReportToolEntry> reporttools;
    public Map<String, TextEditor> editors;
    
    public ViewComponents() {
        entries = new HashMap<>();
        tabletools = new HashMap<>();
        dashboards = new HashMap<>();
        dashboardgroups = new HashMap<>();
        reporttools = new HashMap<>();
        editors = new HashMap<>();
    }
    
    public final void add(AbstractComponentData data) {
        ComponentEntry entry;
        Map<String, ComponentEntry> subentries;
        
        subentries = entries.get(data.type);
        if (subentries == null) {
            subentries = new HashMap<>();
            entries.put(data.type, subentries);
        }
        
        entry = new ComponentEntry();
        entry.data = data;
        subentries.put(data.name, entry);
    }
    
    public final void add(TableToolData data) {
        TableToolEntry entry;
        
        entry = new TableToolEntry();
        entry.data = data;
        tabletools.put(data.name, entry);
    }
    
    public final void add(ReportToolData data) {
        ReportToolEntry entry;
        
        entry = new ReportToolEntry();
        entry.data = data;
        reporttools.put(data.name, entry);
    }
    
    public final void reset() {
        for (Map<String, ComponentEntry> subentries : entries.values())
            subentries.clear();
        tabletools.clear();
        dashboards.clear();
        dashboardgroups.clear();
        reporttools.clear();
        editors.clear();
    }
    
    public final void set(TableToolData data) {
        tabletools.get(data.name).data = data;
    }
}

