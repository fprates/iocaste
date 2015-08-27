package org.iocaste.kernel.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataType;

public class Table {
    private String name;
    private byte sqldb;
    private Map<String, Field> fields;
    private Map<String, Object> values;
    
    public Table(String name, byte sqldb) {
        this.name = name;
        this.sqldb = sqldb;
        fields = new LinkedHashMap<>();
        values = new HashMap<>();
    }
    
    private final void add(String name, int type, int len, int dec, boolean key,
            String tableref, String fieldref, String[] fkc, String[] rfc) {
        Field field = new Field();
        
        field.type = type;
        field.len = len;
        field.dec = dec;
        field.key = key;
        field.tableref = tableref;
        field.fieldref = fieldref;
        field.fkc = fkc;
        field.rfc = rfc;
        fields.put(name, field);
    }
    
    public final void clear() {
        values.clear();
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
        add(constraint,
                DataType.CONSTRAINT, 0, 0, false, tableref, null, fkc, rfc);
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
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        boolean init;
        StringBuilder primarykey = null;
        StringBuilder foreignkey, item, sb;
        Field field = null;
        List<String> items = new ArrayList<>();
        List<String> fks = new ArrayList<>();
        
        for (String fname : fields.keySet()) {
            field = fields.get(fname);
            
            switch (field.type) {
            case DataType.CONSTRAINT:
                foreignkey = new StringBuilder("constraint fk_").
                        append(fname).
                        append(" foreign key (");
                for (int i = 0; i < field.fkc.length; i++) {
                    if (i > 0)
                        foreignkey.append(",");
                    foreignkey.append(field.fkc[i]);
                }
                
                foreignkey.append(") references ").
                        append(field.tableref).
                        append("(");
                for (int i = 0; i < field.rfc.length; i++) {
                    if (i > 0)
                        foreignkey.append(",");
                    foreignkey.append(field.rfc[i]);
                }
                
                foreignkey.append(")");
                fks.add(foreignkey.toString());
                continue;
            case DataType.NUMC:
                item = new StringBuilder(fname).append(" numeric(");
                break;
            case DataType.CHAR:
                item = new StringBuilder(fname).append(" varchar(");
                break;
            case DataType.DATE:
                item = new StringBuilder(fname).append(" date");
                break;
            case DataType.DEC:
                item = new StringBuilder(fname).append(" decimal(");
                break;
            case DataType.TIME:
                item = new StringBuilder(name).append(" time");
                break;
            case DataType.BOOLEAN:
                item = new StringBuilder(fname);
                switch (sqldb) {
                case DBNames.POSTGRES:
                    item.append(" boolean");
                    break;
                default:
                    item.append(" bit");
                    break;
                }
                
                items.add(item.toString());
                continue;
            default:
                item = null;
                break;
            }
            
            switch (field.type) {
            case DataType.CHAR:
            case DataType.NUMC:
                if (field.len > 0)
                    item.append(field.len).append(")");
                
                if (field.dec > 0)
                    item.append(",").append(field.dec).append(")");
                break;
            }
            
            items.add(item.toString());
            
            if (field.tableref != null) {
                foreignkey = new StringBuilder("constraint fk_").
                        append(name).append("_").
                        append(fname).append("_").append(field.tableref).
                        append(" foreign key (").append(fname).
                        append(") references ").append(field.tableref).
                        append("(").append(field.fieldref).append(")");
                fks.add(foreignkey.toString());
            }
            
            if (field.key) {
                if (primarykey == null)
                    primarykey = new StringBuilder("constraint pk_").
                            append(name).
                            append(" primary key(");
                else
                    primarykey.append(", ");
                
                primarykey.append(fname);
            }
        }
        
        sb = new StringBuilder("create table ").append(name).append(" (");
        init = true;
        for (String item_ : items) {
            if (!init)
                sb.append(",");
            sb.append(item_);
            init = false;
        }
        
        if (primarykey != null)
            sb.append(", ").append(primarykey).append(")");
        
        for (String fk : fks)
            sb.append(", ").append(fk);
        
        sb.append(")");
        return sb.toString();
    }
}

class Field {
    public int type, len, dec;
    public boolean key;
    public String tableref, fieldref;
    public String[] fkc, rfc;
}
