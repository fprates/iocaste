package org.iocaste.external.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalElement {
    private String type;
    private String name;
    private ExternalElement[] elements;
    private Map<String, Object> properties;
    private List<ExternalElement> contained;
    private String[] values;
    
    public ExternalElement(ExternalElement container, String type, String name) {
        this.type = type;
        this.name = name;
        properties = new HashMap<String, Object>();
        contained = new ArrayList<ExternalElement>();
        
        if (container == null)
            return;
        
        container.add(this);
    }
    
    public final void add(ExternalElement element) {
        contained.add(element);
    }
    
    public final void flush() {
        int i = 0;
        
        for (ExternalElement element : contained)
            element.flush();

        elements = contained.toArray(new ExternalElement[0]);
        values = new String[properties.size()];
        
        for (String name : properties.keySet())
            values[i++] = new StringBuilder(name).append(":").
                    append(properties.get(name)).toString();
    }
    
    public final ExternalElement[] getElements() {
        return elements;
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getType() {
        return type;
    }
    
    public final String[] getValues() {
        return values;
    }
    
    public final void setElements(ExternalElement[] elements) {
        this.elements = elements;
    }
    
    public final void setValue(String name, Object value) {
        if (properties.containsKey(name))
            properties.remove(name);
        
        properties.put(name, value);
    }
    
    public final void setValues(String[] values) {
        this.values = values;
    }
}
