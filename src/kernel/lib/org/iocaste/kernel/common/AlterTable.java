package org.iocaste.kernel.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataType;

public class AlterTable extends AbstractTableOperation {
    public static final Map<Byte, DBStatements> DB_STATEMENTS;
    
    static {
        DB_STATEMENTS = new HashMap<>();
        for (byte i = DBNames.MYSQL; i <= DBNames.LAST; i++) {
            switch (i) {
            case DBNames.MYSQL:
                DB_STATEMENTS.put(DBNames.MYSQL, instance(
                        " drop foreign key ",
                        " drop primary key",
                        " modify column ",
                        false));
                break;
            default:
                DB_STATEMENTS.put(i, instance(
                        " drop constraint ",
                        " drop constraint pk_",
                        " alter column ",
                        true));
                break;
            }
        }
    }
    
    private static final DBStatements instance(String dropconstraint,
            String dropkey, String modify, boolean appendtablename) {
        DBStatements statements = new DBStatements();
        statements.dropconstraint = dropconstraint;
        statements.dropkey = dropkey;
        statements.modify = modify;
        statements.appendtablename = appendtablename;
        return statements;
    }
    
    public AlterTable(byte sqldb) {
        super(sqldb);
    }

    public AlterTable(String sqldb) {
        super(sqldb);
    }

    public final void compose(List<String> statements, Table table) {
        DBStatements dbstatements;
        String altertable;
        StringBuilder sb;
        Map<String, Field> fields = table.getFields();
        BuildData data = new BuildData();
        
        data.tname = table.getName();
        sb = new StringBuilder("alter table ").append(data.tname);
        altertable = sb.toString();
        dbstatements = DB_STATEMENTS.get(sqldb);
        if (table.isKeyDropped()) {
            sb.append(dbstatements.dropkey);
            if (dbstatements.appendtablename)
                sb.append(data.tname);
            statements.add(sb.toString());
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

        for (String key : data.items.keySet()) {
            sb.setLength(0);
            sb.append(altertable);
            data.field = fields.get(key);
            if (data.field.operation == null)
                sb.append(" add ");
            else
                switch (data.field.operation) {
                case "drop":
                    switch (data.field.type) {
                    case DataType.CONSTRAINT:
                        sb.append(dbstatements.dropconstraint);
                        break;
                    default:
                        sb.append(" drop column ");
                        break;
                    }
                    break;
                case "modify":
                    sb.append(dbstatements.modify);
                    break;
                }
            sb.append(data.items.get(key));
            statements.add(sb.toString());   
        }
        
        if (data.primarykey == null)
            return;
        sb.setLength(0);
        sb.append(altertable).
                append(" add ").append(data.primarykey).append(")");
        statements.add(sb.toString());
    }
}

class DBStatements {
    public String dropconstraint, dropkey, modify;
    public boolean appendtablename;
}