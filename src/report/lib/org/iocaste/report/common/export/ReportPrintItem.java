package org.iocaste.report.common.export;

import java.util.HashMap;
import java.util.Map;

public class ReportPrintItem {
    private String value;
    private Map<String, String> properties;
    
    public ReportPrintItem(String value) {
        this.value = value;
        properties = new HashMap<>();
    }
    
    public final Map<String, String> getProperties() {
        return properties;
    }
    
    public final String getValue() {
        return value;
    }
    
    public final void property(String name, String value) {
        properties.put(name, value);
    }
}
