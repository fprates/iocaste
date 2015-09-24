package org.iocaste.appbuilder.common.tabletool;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.TableItem;

public class TableToolItem {
    private String item;
    private TableToolData data;
    public ExtendedObject object;
    public boolean selected, highlighted;
    
    public TableToolItem(TableToolData data) {
        this.data = data;
    }
    
    public final TableItem get() {
        return data.context.view.getElement(item);
    }
    
    public final void set(TableItem item) {
        this.item = item.getHtmlName();
    }
}
