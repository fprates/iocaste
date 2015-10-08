package org.iocaste.appbuilder.common.tabletool;

import java.util.HashMap;
import java.util.Map;

public class TableToolCells {
    private Map<String, TableToolCell> cells;
    
    public TableToolCells() {
        cells = new HashMap<>();
    }
    
    public final TableToolCell instance(String name) {
        TableToolCell cell = cells.get(name);
        
        if (cell == null) {
            cell = new TableToolCell();
            cells.put(name, cell);
        }
        
        return cell;
    }
    
    public final TableToolCell get(String name) {
        return cells.get(name);
    }
}
