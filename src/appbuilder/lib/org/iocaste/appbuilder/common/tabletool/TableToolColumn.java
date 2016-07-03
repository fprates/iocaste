package org.iocaste.appbuilder.common.tabletool;

import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.TableColumn;

public class TableToolColumn extends AbstractComponentDataItem {
    public String action, text;
    public boolean nolock;
    public TableColumn tcolumn;
    
    public TableToolColumn(String name) {
        super(name);
        componenttype = Const.TEXT_FIELD;
    }
}
