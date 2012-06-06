package org.iocaste.external.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExternalMessage implements Serializable {
    private static final long serialVersionUID = -6788346709638821129L;
    private List<ExternalProperty> values;
    
    public ExternalMessage() {
        values = new ArrayList<ExternalProperty>();
    }
    
    public final void add(String name, boolean value) {
        values.add(newvalue(name, Boolean.toString(value)));
    }
    
    public final void add(String name, byte value) {
        values.add(newvalue(name, Byte.toString(value)));
    }
    
    public final void add(String name, char value) {
        values.add(newvalue(name, Character.toString(value)));
    }
    
    public final void add(String name, double value) {
        values.add(newvalue(name, Double.toString(value)));
    }
    
    public final void add(String name, float value) {
        values.add(newvalue(name, Float.toString(value)));
    }
    
    public final void add(String name, int value) {
        values.add(newvalue(name, Integer.toString(value)));
    }

    public final void add(String name, long value) {
        values.add(newvalue(name, Long.toString(value)));
    }
    
    public final void add(String name, String value) {
        values.add(newvalue(name, value));
    }
    
    public final ExternalProperty[] getValues() {
        return values.toArray(new ExternalProperty[0]);
    }
    
    private final ExternalProperty newvalue(String name, String value) {
        ExternalProperty property = new ExternalProperty();
        
        property.setName(name);
        property.setValue(value);
        
        return property;
    }
    
    public final void setValues(ExternalProperty[] properties) {
        values.clear();
        
        for (ExternalProperty property : properties)
            values.add(property);
    }
}
