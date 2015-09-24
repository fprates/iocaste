package org.iocaste.kernel.common;

import java.util.Map;

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
                    sb.append((init)?
                            " drop column " : ", drop column ");
                    break;
                case "modify":
                    switch (sqldb) {
                    case DBNames.MSSQL1:
                    case DBNames.MSSQL2:
                        sb.append((init)?
                                " alter column " : ", alter column ");
                        break;
                    case DBNames.MYSQL:
                        sb.append((init)?
                                " modify column " : ", modify column ");
                        break;
                    }
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
}
