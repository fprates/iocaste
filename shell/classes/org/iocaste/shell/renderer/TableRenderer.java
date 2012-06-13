package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.XMLElement;
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
        String name;
        Parameter parameter;
        XMLElement trtag, thtag, tabletag = new XMLElement("table");
        List<InputComponent> hidden = new ArrayList<InputComponent>();
        List<XMLElement> tags = new ArrayList<XMLElement>();

        tabletag.add("class", "table_area");
        addEvents(tabletag, table);
        
        if (table.hasHeader()) {
            trtag = new XMLElement("tr");
            
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
        
        for (TableItem item : table.getItens()) {
            tags.clear();
            tags.add(TableItemRenderer.render(table, item, config));
            hidden.addAll(TableItemRenderer.getHidden());
            tabletag.addChildren(tags);
        }
        
        /*
         * componentes de entrada de colunas invisíveis são tratados
         * como parâmetros, pois precisam ter seu conteúdo armazenado.
         */
        for (InputComponent input : hidden) {
            parameter = new Parameter(null, input.getHtmlName());
            parameter.setModelItem(input.getModelItem());
            
            if (input.isBooleanComponent())
                parameter.set((input.isSelected())? "on" : "off");
            else
                parameter.set(input.get());
            
            config.addToForm(ParameterRenderer.render(parameter));
        }
        
        return tabletag;
    }
}
