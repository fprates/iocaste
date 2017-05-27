package org.iocaste.kernel.runtime.shell.tabletool;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.TableItem;

public class TableToolItem {
    private String item;
    private TableToolCells cells;
    public ExtendedObject object;
    public int position;
    public boolean selected, highlighted;
    public TableContext context;
    
    public TableToolItem(TableContext context) {
        this.context = context;
    }
    
    public TableToolCells cells() {
        if (cells == null)
            cells = new TableToolCells();
        return cells;
    }
    
    public final TableItem get() {
        return context.viewctx.view.getElement(item);
    }
    
    public final TableToolCell getCell(String name) {
        return (cells == null)? null : cells.get(name);
    }
    
    public final void set(TableItem item) {
        this.item = item.getHtmlName();
    }
}
