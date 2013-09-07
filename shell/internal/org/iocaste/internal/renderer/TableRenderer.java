package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        TableItem[] itens;
        int vlines, lastline, topline;
        TableItem item;
        XMLElement tag, trtag, thtag, tabletag = new XMLElement("table");
        List<InputComponent> hidden = new ArrayList<>();
        List<XMLElement> tags = new ArrayList<>();

        tabletag.add("class", "table_area");
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
            
            tabletag.addChild(trtag);
        }
        
        itens = table.getItems();
        if (itens.length > 0) {
            tag = new XMLElement("tbody");
            vlines = table.getVisibleLines();
            topline = table.getTopLine();
            
            if (vlines == 0)
                lastline = topline + itens.length;
            else
                lastline = topline + vlines;
            
            for (int i = topline; i < lastline; i++) {
                if (i >= itens.length)
                    continue;
                
                item = itens[i];
                tags.clear();
                tags.add(TableItemRenderer.render(table, item, config));
                hidden.addAll(TableItemRenderer.getHidden());
                tag.addChildren(tags);
            }
            tabletag.addChild(tag);
        }
        
        /*
         * componentes de entrada de colunas invisíveis são tratados
         * como parâmetros, pois precisam ter seu conteúdo armazenado.
         */
        locale = table.getLocale();
        for (InputComponent input : hidden) {
            parameter = new Parameter(null, input.getHtmlName());
            parameter.setModelItem(input.getModelItem());
            parameter.setLocale(locale);
            
            if (input.isBooleanComponent())
                parameter.set((input.isSelected())? "on" : "off");
            else
                parameter.set(input.get());
            
            config.addToForm(ParameterRenderer.render(parameter));
        }
        
        return tabletag;
    }
}
