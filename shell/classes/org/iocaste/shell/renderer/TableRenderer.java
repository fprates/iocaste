package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public class TableRenderer extends Renderer {
    
    /**
     * 
     * @param table
     * @param config
     * @return
     */
    public static final XMLElement render(Table table, Config config) {
        String name;
        XMLElement trtag, thtag, tabletag = new XMLElement("table");
        List<XMLElement> tags = new ArrayList<XMLElement>();

        addAttributes(tabletag, table);
        
        if (table.hasHeader()) {
            trtag = new XMLElement("tr");
            
            for (TableColumn column: table.getColumns()) {
                if (!column.isVisible())
                    continue;
                
                if (column.isMark() && !table.hasMark())
                    continue;
                
                thtag = new XMLElement("th");
                name = column.getName();
                if (name != null)
                    thtag.addInner(name);
                
                trtag.addChild(thtag);
            }
            
            tabletag.addChild(trtag);
        }
        
        for (TableItem item : table.getItens()) {
            tags.clear();
            tags.add(TableItemRenderer.render(table, item, config));
            tabletag.addChildren(tags);
        }
        
        return tabletag;
    }
}
