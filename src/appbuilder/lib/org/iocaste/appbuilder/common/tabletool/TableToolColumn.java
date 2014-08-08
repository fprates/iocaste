package org.iocaste.appbuilder.common.tabletool;

import java.io.Serializable;
import java.util.Map;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.TableColumn;

public class TableToolColumn implements Serializable {
    private static final long serialVersionUID = -4664704410408423891L;
    public String name, sh, action;
    public boolean disabled;
    public Const type;
    public Map<String, Object> values;
    public TableColumn tcolumn;
    public int size;
    
    public TableToolColumn(TableToolData data, String name) {
        this.name = name;
        data.columns.put(name, this);
    }
}
