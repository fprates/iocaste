package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public class TableItemRenderer extends Renderer {
    private static List<InputComponent> hidden;
    
    /**
     * 
     * @return
     */
    public static final List<InputComponent> getHidden() {
        return hidden;
    }
    
    /**
     * 
     * @param table
     * @param item
     * @param config
     * @return
     */
    public static final XMLElement render(Table table, TableItem item,
            Config config) {
        boolean mark, savemark;
        Component component;
        TableColumn column;
        String text, style;
        TableColumn[] columns = table.getColumns();
        int i = 0;
        XMLElement tdtag, trtag = new XMLElement("tr");
        List<XMLElement> tags = new ArrayList<>();
        
        savemark = true;
        hidden = new ArrayList<>();
        style = item.getStyleClass();
        trtag.add("class", style);
        
        for (Element element : item.getElements()) {
            column = columns[i++];
            mark = column.isMark();
            if (mark && !table.hasMark())
                continue;
            
            if (!column.isVisible() || !item.isVisible()) {
                if (element.isDataStorable())
                    hidden.add((InputComponent)element);
                continue;
            }
            
            tdtag = new XMLElement("td");
            tdtag.add("class", "table_cell");
            
            if (element != null) {
                tags.clear();
                if (column.getRenderTextOnly()) {
                    if (!(element instanceof Component)) {
                        tdtag.addInner("");
                    } else {
                        component = (Component)element;
                        text = component.getText();
                        tdtag.addInner(text);
                    }
                } else {
                    /*
                     * se existir mark, deve vir habilitada de qualquer jeito.
                     * (a menos que o componente esteja explicitamente
                     * desabilitado.
                     */
                    if (mark) {
                        savemark = table.isEnabled();
                        table.setEnabled(true);
                    }
                    renderElement(tags, element, config);
                    if (mark)
                        table.setEnabled(savemark);
                    tdtag.addChildren(tags);
                }
            }
            
            trtag.addChild(tdtag);
        }
        
        return trtag;
    }
}
