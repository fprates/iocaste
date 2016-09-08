package org.iocaste.appbuilder.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ExtendedObject;

public class DataConversion {
    public static final int NEXT_NUMBER = 0;
    public static final int CONSTANT = 1;
    public static final int IGNORE = 2;
    public static final byte DATAFORM = 1;
    public static final byte OBJECT = 2;
    public static final byte OBJECTS = 3;
    public static final byte TABLETOOL = 4;
    public static final byte COLLECTION = 5;
    private String to, sourcepage;
    private Map<String, FieldConversion> fields;
    private Object source;
    private byte sourcetype;
    private DataConversionRule rule;
    
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
    
    public final void dfsource(String dataform) {
        dfsource(null, dataform);
    }
    
    public final void dfsource(String page, String dataform) {
        sourcetype = DATAFORM;
        source = dataform;
        sourcepage = page;
    }
    
    public final byte getSourceType() {
        return sourcetype;
    }
    
    public final Set<String> getFields() {
        return fields.keySet();
    }
    
    public final Object getParameter(String field) {
        return fields.get(field).parameter;
    }
    
    public final DataConversionRule getRule() {
        return rule;
    }
    
    public final Object getSource() {
        return source;
    }
    
    public final String getSourcePage() {
        return sourcepage;
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
    
    public final void nextnumber(String field, String factory, String series) {
        put(field, NEXT_NUMBER, factory, series);
    }
    
    private final void put(String field, int type, Object value) {
        fields.put(field, new FieldConversion(type, value));
    }
    
    private final void put(String field, int type, Object value, Object param) {
        fields.put(field, new FieldConversion(type, value, param));
    }
    
    public final void rule(DataConversionRule rule) {
        this.rule = rule;
    }
    
    public final void source(ExtendedObject source) {
        sourcetype = OBJECT;
        this.source = source;
    }
    
    public final void source(ExtendedObject[] source) {
        sourcetype = OBJECTS;
        this.source = source;
    }
    
    public final void source(Collection<ExtendedObject> source) {
        sourcetype = COLLECTION;
        this.source = source;
    }
    
    public final void tbsource(String tabletool) {
        tbsource(null, tabletool);
    }
    
    public final void tbsource(String page, String tabletool) {
        sourcetype = TABLETOOL;
        source = tabletool;
        sourcepage = page;
    }
    
    public final void to(String model) {
        to = model;
    }
}

class FieldConversion {
    public int type;
    public Object value, parameter;
    
    public FieldConversion(int type, Object value) {
        this.type = type;
        this.value = value;
    }
    
    public FieldConversion(int type, Object value, Object parameter) {
        this.type = type;
        this.value = value;
        this.parameter = parameter;
    }
}