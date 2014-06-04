package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataConversion {
    public static final int NEXT_NUMBER = 0;
    public static final int CONSTANT = 1;
    public static final int IGNORE = 2;
    private String to;
    private Map<String, FieldConversion> fields;
    
    public DataConversion(String tomodel) {
        fields = new HashMap<>();
        to = tomodel;
    }
    
    public DataConversion() {
        fields = new HashMap<>();
    }
    
    public final void constant(String field, Object value) {
        put(field, CONSTANT, value);
    }
    
    public final Set<String> getFields() {
        return fields.keySet();
    }
    
    public final String getTo() {
        return to;
    }
    
    public final int getType(String field) {
        return fields.get(field).type;
    }
    
    public final Object getValue(String field) {
        return fields.get(field).value;
    }
    
    public final void ignore(String field) {
        put(field, IGNORE, null);
    }
    
    public final void nextnumber(String field, String factory) {
        put(field, NEXT_NUMBER, factory);
    }
    
    private final void put(String field, int type, Object value) {
        fields.put(field, new FieldConversion(type, value));
    }
    
    public final void to(String model) {
        to = model;
    }
}

class FieldConversion {
    public int type;
    public Object value;
    
    public FieldConversion(int type, Object value) {
        this.type = type;
        this.value = value;
    }
}