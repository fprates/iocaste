package org.iocaste.kernel.common;

import java.util.Map;

public class CreateTable extends AbstractTableOperation {

    public CreateTable(byte sqldb) {
        super(sqldb);
    }
    
    public CreateTable(String sqldb) {
        super(sqldb);
    }
    
    public String compose(Table table) {
        boolean init;
        StringBuilder sb;
        Map<String, Field> fields = table.getFields();
        BuildData data = new BuildData();
        
        data.tname = table.getName();
        for (String fname : fields.keySet()) {
            data.field = fields.get(fname);
            data.fname = fname;
            buildItem(data);
        }
        
        sb = new StringBuilder("create table ").append(data.tname).append(" (");
        init = true;
        for (String key : data.items.keySet()) {
            if (!init)
                sb.append(",");
            sb.append(data.items.get(key));
            init = false;
        }
        
        if (data.primarykey != null)
            sb.append(", ").append(data.primarykey).append(")");
        
        for (String fk : data.fks)
            sb.append(", ").append(fk);
        
        sb.append(")");
        return sb.toString();
    }
}
