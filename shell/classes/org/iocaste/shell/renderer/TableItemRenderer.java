package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public class TableItemRenderer extends Renderer {
    
    /**
     * 
     * @param table
     * @param item
     * @param config
     * @return
     */
    public static final XMLElement render(Table table, TableItem item,
            Config config) {
        TableColumn column;
        TableColumn[] columns = table.getColumns();
        int i = 0;
        XMLElement tdtag, trtag = new XMLElement("tr");
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        for (Element element : item.getElements()) {
            column = columns[i++];
            
            if (!column.isVisible())
                continue;
            
            if (column.isMark() && !table.hasMark())
                continue;
            
            tdtag = new XMLElement("td");
            
            if (element != null) {
                tags.clear();
                renderElement(tags, element, config);
                tdtag.addChildren(tags);
            }
            
            trtag.addChild(tdtag);
        }
        
        return trtag;
    }
}
