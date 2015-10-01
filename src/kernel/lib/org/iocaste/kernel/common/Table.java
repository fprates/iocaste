package org.iocaste.kernel.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DataType;

public class Table {
    public static final byte REGULAR = 0;
    public static final byte KEY = 1;
    public static final byte REFERENCE = 2;
    private String name;
    private boolean droppedkey;
    private Map<String, Field> fields;
    private Map<String, Object> values;
    
    public Table(String name) {
        this.name = name;
        fields = new LinkedHashMap<>();
        values = new HashMap<>();
    }
    
    private final void add(String name, int type, int len, int dec, boolean key,
            String tableref, String fieldref, String fkc, String rfc) {
        Field field;
        
        field = fields.get(name);
        if (field == null) {
            field = new Field();
            fields.put(name, field);
        }
        
        field.type = type;
        field.len = len;
        field.dec = dec;
        field.key = key;
        field.tableref = tableref;
        field.fieldref = fieldref;
        if (fkc == null)
            return;
        field.fkc.add(fkc);
        field.rfc.add(rfc);
    }
    
    public final void clear() {
        droppedkey = false;
        values.clear();
    }
    
    private final void drop(String name, byte type) {
        Field field;
        
        switch (type) {
        case KEY:
            droppedkey = true; 
            return;
        case REFERENCE:
            field = new Field();
            field.type = DataType.CONSTRAINT;
            break;
        default:
            field = new Field();
            field.type = 0;
            break;
        }

        field.operation = "drop";
        fields.put(name, field);
    }
    
    public final void drop(String name) {
        drop(name, REGULAR);
    }
    
    public final void dropkey(String name) {
        drop(name, KEY);
    }
    
    public final void dropreference(String name) {
        drop(name, REFERENCE);
    }
    
    public final Map<String, Object> getValues() {
        return values;
    }
    
    public final void add(String name, int type, int len) {
        add(name, type, len, 0, false, null, null, null, null);
    }
    
    public final void add(String name, int type, int len, int dec) {
        add(name, type, len, dec, false, null, null, null, null);
    }
    
    public final void constraint(String constraint, String tableref,
            String[] fkc, String[] rfc) {
        for (int i = 0; i < fkc.length; i++)
            add(constraint, DataType.CONSTRAINT,
                    0, 0, false, tableref, null, fkc[i], rfc[i]);
    }
    
    public final void constraint(String constraint, String tableref,
            String fkc, String rfc) {
        add(constraint,
                DataType.CONSTRAINT, 0, 0, false, tableref, null, fkc, rfc);
    }
    
    public final boolean isKeyDropped() {
        return droppedkey;
    }
    
    public final Map<String, Field> getFields() {
        return fields;
    }
    
    public final String getName() {
        return name;
    }
    
    public final int getType(String field) {
        return fields.get(field).type;
    }
    
    public final void key(String name, int type, int len) {
        add(name, type, len, 0, true, null, null, null, null);
    }
    
    public final void key(String name, int type, int len, int dec) {
        add(name, type, len, dec, true, null, null, null, null);
    }
    
    public final void pkconstraint(String constraint, String fieldname) {
        Field field;
        
        field = fields.get(constraint);
        if (field == null) {
            field = new Field();
            field.key = true;
            field.type = DataType.CONSTRAINT;
            fields.put(constraint, field);
        }
        
        field.pks.add(fieldname);
    }
    
    public final void ref(String name, int type, int len, String tableref,
            String fieldref) {
        add(name, type, len, 0, false, tableref, fieldref, null, null);
    }
    
    public final void ref(String name, int type, int dec, int len,
            String tableref, String fieldref) {
        add(name, type, len, dec, false, tableref, fieldref, null, null);
    }
    
    public final void set(String name, Object value) {
        values.put(name.toUpperCase(), value);
    }
    
    public final void update(String name, int type, int len, int dec) {
        Field field;
        
        field = new Field();
        field.type = type;
        field.len = len;
        field.dec = dec;
        field.operation = "modify";
        fields.put(name, field);
    }
}
