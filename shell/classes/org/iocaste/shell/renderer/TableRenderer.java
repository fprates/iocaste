package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Parameter;
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
