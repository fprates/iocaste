package org.iocaste.kernel.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataType;

public abstract class AbstractTableOperation {
    protected byte sqldb;

    public AbstractTableOperation(byte sqldb) {
        this.sqldb = sqldb;
    }
    
    public AbstractTableOperation(String sqldb) {
        switch (sqldb) {
        case "postgres":
            this.sqldb = DBNames.POSTGRES;
            break;
        case "mssql1":
            this.sqldb = DBNames.MSSQL1;
            break;
        case "mssql2":
            this.sqldb = DBNames.MSSQL2;
            break;
        default:
            this.sqldb = DBNames.MYSQL;
            break;
        }
    }

    private final void addpk(BuildData data) {
        if (data.primarykey == null)
            data.primarykey = new StringBuilder("constraint pk_").
                    append(data.tname).
                    append(" primary key(");
        else
            data.primarykey.append(", ");
        
        data.primarykey.append(data.fname);
    }
    
    protected final void buildItem(BuildData data) {
        StringBuilder foreignkey, item;
        boolean init;
        
        switch (data.field.type) {
        case DataType.CONSTRAINT:
            if (data.field.key) {
                for (String pk : data.field.pks) {
                    data.fname = pk;
                    addpk(data);
                }
                return;
            }
            
            foreignkey = new StringBuilder("constraint fk_").
                    append(data.fname).
                    append(" foreign key (");
            init = true;
            for (String fk : data.field.fkc) {
                if (init)
                    init = false;
                else
                    foreignkey.append(",");
                foreignkey.append(fk);
            }
            
            foreignkey.append(") references ").
                    append(data.field.tableref).
                    append("(");
            init = true;
            for (String rf : data.field.rfc) {
                if (init)
                    init = false;
                else
                    foreignkey.append(",");
                foreignkey.append(rf);
            }
            
            foreignkey.append(")");
            data.fks.add(foreignkey.toString());
            return;
        case DataType.NUMC:
            item = new StringBuilder(data.fname).append(" numeric(");
            break;
        case DataType.CHAR:
            item = new StringBuilder(data.fname).append(" varchar(");
            break;
        case DataType.DATE:
            switch (sqldb) {
            case DBNames.MSSQL1:
                item = new StringBuilder(data.fname).append(" datetime");
                break;
            default:
                item = new StringBuilder(data.fname).append(" date");
                break;
            }
            break;
        case DataType.DEC:
            item = new StringBuilder(data.fname).append(" decimal(");
            break;
        case DataType.TIME:
            switch (sqldb) {
            case DBNames.MSSQL1:
                item = new StringBuilder(data.fname).append(" datetime");
                break;
            default:
                item = new StringBuilder(data.tname).append(" time");
                break;
            }
            
            break;
        case DataType.BOOLEAN:
            item = new StringBuilder(data.fname);
            switch (sqldb) {
            case DBNames.POSTGRES:
                item.append(" boolean");
                break;
            default:
                item.append(" bit");
                break;
            }
            
            data.items.put(data.fname, item.toString());
            return;
        default:
            item = null;
            break;
        }
        
        switch (data.field.type) {
        case DataType.CHAR:
        case DataType.NUMC:
        case DataType.DEC:
            if (data.field.len > 0)
                item.append(data.field.len);
            
            if (data.field.dec > 0)
                item.append(",").append(data.field.dec);
            
            item.append(")");
            break;
        }
        
        data.items.put(data.fname, item.toString());
        
        if (data.field.tableref != null) {
            foreignkey = new StringBuilder("constraint fk_").
                    append(data.tname).append("_").
                    append(data.fname).append("_").append(data.field.tableref).
                    append(" foreign key (").append(data.fname).
                    append(") references ").append(data.field.tableref).
                    append("(").append(data.field.fieldref).append(")");
            data.fks.add(foreignkey.toString());
        }
        
        if (data.field.key)
            addpk(data);
    }
}

class BuildData {
    public String tname, fname;
    public Field field;
    public Map<String, String> items; 
    public List<String> fks;
    public StringBuilder primarykey;
    
    public BuildData() {
        items = new LinkedHashMap<>();
        fks = new ArrayList<>();
    }
}