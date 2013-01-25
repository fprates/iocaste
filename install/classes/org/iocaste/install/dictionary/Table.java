package org.iocaste.install.dictionary;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.install.DBNames;

public class Table {
    private String name;
    private byte sqldb;
    private Map<String, Field> fields;
    private Map<String, Object> values;
    
    public Table(String name) {
        this.name = name;
        fields = new LinkedHashMap<>();
        values = new HashMap<>();
    }
    
    private final void add(String name, byte type, int len, boolean key,
            String tableref, String fieldref) {
        Field field = new Field();
        
        field.type = type;
        field.len = len;
        field.key = key;
        field.tableref = tableref;
        field.fieldref = fieldref;
        fields.put(name, field);
    }
    
    public final Map<String, Object> getValues() {
        return values;
    }
    
    public final void add(String name, byte type, int len) {
        add(name, type, len, false, null, null);
    }
    
    public final String getName() {
        return name;
    }
    
    public final byte getType(String field) {
        return fields.get(field).type;
    }
    
    public final void key(String name, byte type, int len) {
        add(name, type, len, true, null, null);
    }
    
    public final void ref(String name, byte type, int len, String tableref,
            String fieldref) {
        add(name, type, len, false, tableref, fieldref);
    }
    
    public final void set(String name, Object value) {
        values.put(name.toUpperCase(), value);
    }
    
    public final void setSQLDB(byte sqldb) {
        this.sqldb = sqldb;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Field field = null;
        StringBuilder sb = new StringBuilder("create table ").
                append(name).append(" (");
        
        for (String fname : fields.keySet()) {
            if (field != null)
                sb.append(", ");
            
            field = fields.get(fname);
            
            sb.append(fname);
            switch (field.type) {
            case Module.NUMC:
                sb.append(" numeric(");
                break;
            case Module.CHAR:
                sb.append(" varchar(");
                break;
            case Module.BOOLEAN:
                switch (sqldb) {
                case DBNames.POSTGRES:
                    sb.append(" boolean");
                    break;
                default:
                    sb.append(" bit");
                    break;
                }
                break;
            }
            
            if (field.len > 0 && field.type != Module.BOOLEAN)
                sb.append(field.len).append(")");
            
            if (field.tableref != null) {
                switch (sqldb) {
                case DBNames.MSSQL:
                case DBNames.HSQLDB:
                    sb.append(" foreign key references ");
                    break;
                case DBNames.MYSQL:
                case DBNames.POSTGRES:
                    sb.append(" references ");
                    break;
                }
                
                sb.append(field.tableref).append("(").append(field.fieldref).
                append(")");
            }
            
            if (field.key)
                sb.append(" primary key");
        }
        
        sb.append(")");
        return sb.toString();
    }
}

class Field {
    public byte type;
    public int len;
    public boolean key;
    public String tableref, fieldref;
}
