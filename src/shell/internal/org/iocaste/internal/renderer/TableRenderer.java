package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.InputComponent;
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
        String title, name, text;
        Set<TableItem> items;
        XMLElement tag, trtag, thtag, divtag;
        XMLElement tabletag = new XMLElement("table");
        List<InputComponent> hidden = new ArrayList<>();
        List<XMLElement> tags = new ArrayList<>();

        tabletag.add("class", table.getStyleClass());
        addEvents(tabletag, table);
        
        title = table.getText();
        if (title != null) {
            tag = new XMLElement("caption");
            tag.add("class", "tbcaption");
            tag.addInner(title);
            tabletag.addChild(tag);
        }
        
        if (table.hasHeader()) {
            tag = new XMLElement("thead");
            trtag = new XMLElement("tr");
            tag.addChild(trtag);
            
            for (TableColumn column: table.getColumns()) {
                if ((column.isMark() && !table.hasMark()) ||
                        !column.isVisible())
                    continue;
                
                thtag = new XMLElement("th");
                thtag.add("class", "table_header");
                text = column.getText();
                if (text == null) {
                    name = column.getName();
                    if (name != null)
                        text = name;
                }
                
                thtag.addInner(text);
                trtag.addChild(thtag);
            }
            
            tabletag.addChild(tag);
        }
        
        items = table.getItems();
        if (items.size() == 0) {
            tabletag.addInner("");
        } else {
            tag = new XMLElement("tbody");
            for (TableItem item : items) {
                if (!item.isVisible())
                    continue;
                tags.clear();
                tags.add(TableItemRenderer.render(table, item, config));
                hidden.addAll(TableItemRenderer.getHidden());
                tag.addChildren(tags);
            }
            
            tabletag.addChild(tag);
        }
        
        divtag = new XMLElement("div");
        divtag.add("class", table.getBorderStyle());
        divtag.addChild(tabletag);
        return divtag;
    }
}
