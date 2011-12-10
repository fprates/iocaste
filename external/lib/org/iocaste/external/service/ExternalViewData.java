package org.iocaste.external.service;

import java.util.HashMap;
import java.util.Map;

public class ExternalViewData {
    private String title;
    private String name;
    private ExternalElement[] containers;
    private String[] values;
    private Map<String, String[]> properties;
    
    public ExternalViewData(String name) {
        this.name = name;
        properties = new HashMap<String, String[]>();
    }
    
    public final void build() {
        StringBuilder sb;
        int i = 0;
        
        for (ExternalElement container : containers)
            container.flush();
        
        values = new String[properties.size()];
        for (String name : properties.keySet()) {
            sb = new StringBuilder(name);
            
            for (String value : properties.get(name))
                sb.append(":").append(value);
            
            values[i++] = sb.toString();
        }
    }
    
    public final ExternalElement[] getContainers() {
        return containers;
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getTitle() {
        return title;
    }
    
    public final String[] getValues() {
        return values;
    }
    
    public final void setContainers(ExternalElement[] containers) {
        this.containers = containers;
    }
    
    public final void setTitle(String title) {
        this.title = title;
    }
    
    public final void setValue(String name, String[] values) {
        properties.put(name, values);
    }
    
    public final void setValues(String[] values) {
        this.values = values;
    }
}
