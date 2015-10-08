package org.iocaste.appbuilder.common.tabletool;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.TableItem;

public class TableToolItem {
    private String item;
    private TableToolData data;
    private TableToolCells cells;
    public ExtendedObject object;
    public boolean selected, highlighted;
    
    public TableToolItem(TableToolData data) {
        this.data = data;
    }
    
    public TableToolCells cells() {
        if (cells == null)
            cells = new TableToolCells();
        return cells;
    }
    
    public final TableItem get() {
        return data.context.view.getElement(item);
    }
    
    public final TableToolCell getCell(String name) {
        return (cells == null)? null : cells.get(name);
    }
    
    public final void set(TableItem item) {
        this.item = item.getHtmlName();
    }
}
