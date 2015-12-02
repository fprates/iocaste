package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.reporttool.ReportToolEntry;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolEntry;
import org.iocaste.texteditor.common.TextEditor;

public class ViewComponents {
    public Map<String, ComponentEntry> entries;
    public Map<String, TableToolEntry> tabletools;
    public Map<String, DashboardFactory> dashboards;
    public Map<String, DashboardComponent> dashboardgroups;
    public Map<String, ReportToolEntry> reporttools;
    public Map<String, TextEditor> editors;
    
    public ViewComponents() {
        entries = new LinkedHashMap<>();
        tabletools = new HashMap<>();
        dashboards = new HashMap<>();
        dashboardgroups = new HashMap<>();
        reporttools = new HashMap<>();
        editors = new HashMap<>();
    }
    
    public final void add(AbstractComponentData data) {
        ComponentEntry entry = new ComponentEntry();
        entry.data = data;
        entries.put(data.name, entry);
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
        entries.clear();
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

