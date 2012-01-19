package org.iocaste.external.service;

import java.util.HashMap;
import java.util.Map;

public class ExternalMessage implements ExternalMessageProtocol {
    private Map<String, ExternalProperty> values;
    
    public ExternalMessage() {
        values = new HashMap<String, ExternalProperty>();
    }
    
    private final void newProperty(String name, ExternalTypes type,
            Object value) {
        ExternalProperty property = new ExternalProperty();
        
        property.setName(name);
        property.setValue(value);
        property.setType(type);
        
        values.put(name, property);
    }
    
    public final void add(String name, boolean value) {
        newProperty(name, ExternalTypes.BOOLEAN, value);
    }
    
    public final void add(String name, char value) {
        newProperty(name, ExternalTypes.CHAR, value);
    }
    
    public final void add(String name, double value) {
        newProperty(name, ExternalTypes.DOUBLE, value);
    }
    
    public final void add(String name, ExternalMessageProtocol value) {
        newProperty(name, ExternalTypes.MESSAGE, value);
    }
    
    public final void add(String name, ExternalPropertyProtocol value) {
        newProperty(name, ExternalTypes.PROPERTY, value);
    }
    
    public final void add(String name, int value) {
        newProperty(name, ExternalTypes.INTEGER, value);
    }
    
    public final void add(String name, float value) {
        newProperty(name, ExternalTypes.FLOAT, value);
    }
    
    public final void add(String name, long value) {
        newProperty(name, ExternalTypes.LONG, value);
    }
    
    public final void add(String name, short value) {
        newProperty(name, ExternalTypes.SHORT, value);
    }
    
    public final void add(String name, String value) {
        newProperty(name, ExternalTypes.STRING, value);
    }
    
    protected final ExternalPropertyProtocol get(String name) {
        return values.get(name);
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalMessageProtocol#getProperties()
     */
    @Override
    public final ExternalPropertyProtocol[] getProperties() {
        return values.values().toArray(new ExternalProperty[0]);
    }
}
