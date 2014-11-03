package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public class TableRenderer extends Renderer {
    
    private static final Container buildLineControl(Table table) {
        Link link;
        String action, tablename = table.getName();
        String name = new StringBuilder(tablename).append(".linecontrol").
                toString();
        Container linecontrol = new StandardContainer(table, name);
        
        action = table.getAction(Table.ADD);
        if (action != null) {
            link = new Link(linecontrol, new StringBuilder(name).append(".add").
                    toString(), action);
            link.setImage("images/insitem.svg");
            link.setStyleClass("imglink");
        }
        
        action = table.getAction(Table.REMOVE);
        if (action != null) {
            link = new Link(linecontrol, new StringBuilder(name).
                    append(".remove").toString(), action);
            link.setImage("images/remitem.svg");
            link.setStyleClass("imglink");
        }
        
        return linecontrol;
    }
    
    /**
     * 
     * @param table
     * @param config
     * @return
     */
    public static final XMLElement render(Table table, Config config) {
        Locale locale;
        String title, name;
        Parameter parameter;
        Set<TableItem> items;
        int vlines, lastline, topline, size, i;
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
            tag.addInner(config.getText(title, title));
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
                
                if (column.isMark() && table.hasMark()) {
                    if (table.getLineControl() == null)
                        table.setLineControl(buildLineControl(table));
                    
                    thtag.addChildren(Renderer.renderElements(
                            table.getLineControl().getElements(), config));
                    
                } else {
                    name = column.getName();
                    if (name != null)
                        thtag.addInner(config.getText(name, name));
                }
                
                trtag.addChild(thtag);
            }
            
            tabletag.addChild(tag);
        }
        
        items = table.getItems();
        size = items.size();
        if (items.size() > 0) {
            tag = new XMLElement("tbody");
            vlines = table.getVisibleLines();
            topline = table.getTopLine();
            
            if (vlines == 0)
                lastline = topline + size;
            else
                lastline = topline + vlines;
            
            i = 0;
            for (TableItem item : items) {
                if (i++ < topline)
                    continue;
                
                if ((i - 1) == lastline)
                    break;
                
                tags.clear();
                tags.add(TableItemRenderer.render(table, item, config));
                hidden.addAll(TableItemRenderer.getHidden());
                tag.addChildren(tags);
            }
            
            tabletag.addChild(tag);
        }
        
        divtag = new XMLElement("div");
        divtag.add("style", table.getBorderStyle());
        divtag.addChild(tabletag);
        
        /*
         * componentes de entrada de colunas invisíveis são tratados
         * como parâmetros, pois precisam ter seu conteúdo armazenado.
         */
        locale = table.getLocale();
        for (InputComponent input : hidden) {
            parameter = new Parameter(config.getView(), input.getHtmlName());
            parameter.setModelItem(input.getModelItem());
            parameter.setLocale(locale);
            
            if (input.isBooleanComponent())
                parameter.set((input.isSelected())? "on" : "off");
            else
                parameter.set(input.get());
            
            divtag.addChild(ParameterRenderer.render(parameter));
        }
        return divtag;
    }
}
