package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableContextItem;
import org.iocaste.shell.common.TableItem;

public class TableRenderer extends AbstractElementRenderer<Table> {
    
    public TableRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.TABLE);
    }

    @Override
    protected final XMLElement execute(Table table, Config config) {
        String title, name, text;
        Set<TableItem> items;
        ContextMenu ctxmenu;
        TableContextItem ctxitem;
        Map<String, TableContextItem> ctxitems;
        XMLElement tag, trtag, thtag, divtag;
        TableItemRenderer itemrenderer;
        XMLElement tabletag = new XMLElement("table");
        List<InputComponent> hidden = new ArrayList<>();
        List<XMLElement> tags = new ArrayList<>();

        tabletag.add("class", table.getStyleClass());
        addAttributes(tabletag, table);
        
        title = table.getText();
        if (title != null) {
            tag = new XMLElement("caption");
            tag.add("class", "tbcaption");
            tag.addInner(title);
            tabletag.addChild(tag);
        }
        
        if (table.hasHeader()) {
            tag = new XMLElement("thead");
            tag.add("class", "table_head");
            trtag = new XMLElement("tr");
            trtag.add("class", "table_line");
            tag.addChild(trtag);
            tabletag.addChild(tag);
            
            for (TableColumn column: table.getColumns()) {
                if ((column.isMark() && !table.hasMark()) ||
                        !column.isVisible())
                    continue;
                
                thtag = new XMLElement("th");
                thtag.add("class", "table_header");
                if (column.isMark()) {
                    tag = new XMLElement("ul");
                    tag.add("style",
                            "margin:0px;padding:0px;list-style-type:none");
                    ctxmenu = new ContextMenu(tag, "mark");
                    ctxitems = table.getContextItems();
                    for (String itemname : ctxitems.keySet()) {
                        ctxitem = ctxitems.get(itemname);
                        if (ctxitem.visible)
                            ctxmenu.add(ctxitem.htmlname, config, ctxitem.text);
                    }
                    thtag.addChild(tag);
                } else {
                    text = column.getText();
                    if (text == null) {
                        name = column.getName();
                        if (name != null)
                            text = name;
                    }
                    thtag.addInner(text);
                }
                trtag.addChild(thtag);
            }
        }
        
        items = table.getItems();
        if (items.size() == 0) {
            tabletag.addInner("");
        } else {
            itemrenderer = get(Const.TABLE_ITEM);
            tag = new XMLElement("tbody");
            for (TableItem item : items) {
                if (!item.isVisible())
                    continue;
                tags.clear();
                tags.add(itemrenderer.run(item, config));
                hidden.addAll(itemrenderer.getHidden());
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
