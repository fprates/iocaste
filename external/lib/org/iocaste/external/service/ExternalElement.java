package org.iocaste.external.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalElement {
    private String type;
    private String name;
    private List<ExternalElement> elements;
    private Map<String, Object> values;
    
    public ExternalElement(ExternalElement container, String type, String name) {
        this.type = type;
        this.name = name;
        elements = new ArrayList<ExternalElement>();
        values = new HashMap<String, Object>();
        
        if (container == null)
            return;

        container.add(this);
    }
    
    public final void add(ExternalElement element) {
        elements.add(element);
    }
    
    public final double getDouble(String name) {
        return (Double)values.get(name);
    }
    
    public final int getInt(String name) {
        return (Integer)values.get(name);
    }
    
    public final long getLong(String name) {
        return (Long)values.get(name);
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getString(String name) {
        return (String)values.get(name);
    }
    
    public final String getType() {
        return type;
    }
    
    public final void setValue(String name, Object value) {
        if (values.containsKey(name))
            values.remove(name);
        
        values.put(name, value);
    }
}
