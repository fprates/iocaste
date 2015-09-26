package org.iocaste.kernel.common;

import java.util.Map;

import org.iocaste.documents.common.DataType;

public class AlterTable extends AbstractTableOperation {

    public AlterTable(byte sqldb) {
        super(sqldb);
    }

    public AlterTable(String sqldb) {
        super(sqldb);
    }

    public String compose(Table table) {
        boolean init;
        StringBuilder sb;
        Map<String, Field> fields = table.getFields();
        BuildData data = new BuildData();
        
        data.tname = table.getName();
        sb = new StringBuilder("alter table ").append(data.tname);
        if (table.isKeyDropped())
            switch (sqldb) {
            case DBNames.MYSQL:
                sb.append(" drop primary key");
                return sb.toString();
            default:
                sb.append(" drop constraint pk_").append(data.tname);
                return sb.toString();
            }
        
        for (String fname : fields.keySet()) {
            data.field = fields.get(fname);
            data.fname = fname;
            if ((data.field.operation != null) &&
                    data.field.operation.equals("drop")) {
                data.items.put(fname, fname);
                continue;
            }
            buildItem(data);
        }

        init = true;
        for (String key : data.items.keySet()) {
            data.field = fields.get(key);
            if (data.field.operation == null)
                sb.append((init)? " add " : ", add ");
            else
                switch (data.field.operation) {
                case "drop":
                    drop(sb, data, init);
                    break;
                case "modify":
                    modify(sb, init);
                    break;
                }
            sb.append(data.items.get(key));
            init = false;
        }
        
        if (data.primarykey != null) {
            sb.append((init)? " add ": ", add ");
            sb.append(data.primarykey).append(")");
        }
        
        return sb.toString();
    }
    
    private final void drop(StringBuilder sb, BuildData data, boolean init) {
        switch (data.field.type) {
        case DataType.CONSTRAINT:
            switch (sqldb) {
            case DBNames.MYSQL:
                sb.append((init)? " drop foreign key " : ", drop foreign key ");
                break;
            default:
                sb.append((init)? " drop constraint " : ", drop constraint ");
                break;
            }
            break;
        default:
            sb.append((init)?
                    " drop column " : ", drop column ");
            break;
        }
    }
    
    private final void modify(StringBuilder sb, boolean init) {
        switch (sqldb) {
        case DBNames.MYSQL:
            sb.append((init)?
                    " modify column " : ", modify column ");
            break;
        default:
            sb.append((init)?
                    " alter column " : ", alter column ");
            break;
        }
    }
}
