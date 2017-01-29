package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.internal.Controller;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public class TableItemRenderer extends AbstractElementRenderer<TableItem> {
    private List<InputComponent> hidden;
    
    public TableItemRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.TABLE_ITEM);
    }
    
    /**
     * 
     * @return
     */
    public final List<InputComponent> getHidden() {
        return hidden;
    }
    
    @Override
    protected final XMLElement execute(TableItem item, Config config) {
        boolean mark, savemark;
        Component component;
        TableColumn column;
        String text, style, tdstyle, select;
        Table table = (Table)item.getContainer();
        TableColumn[] columns = table.getColumns();
        int i = 0;
        XMLElement tdtag, tdinner, tdinnerlabel, tdinnercontent;
        XMLElement trtag = new XMLElement("tr");
        List<XMLElement> tags = new ArrayList<>();
        
        savemark = true;
        hidden = new ArrayList<>();
        style = item.getStyleClass();
        trtag.add("class",
                (style == null)? table.getStyleClass(Table.TABLE_LINE) : style);
        tdstyle = table.getStyleClass(Table.TABLE_CELL);
        select = Controller.messages.get("select");
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
            tdtag.add("class", tdstyle);
            
            tdinnerlabel = new XMLElement("li");
            tdinnerlabel.add("class", "td_label");
            tdinnerlabel.addInner((mark)? select : column.getText());
            
            tdinnercontent = new XMLElement("li");
            if (element != null) {
                tags.clear();
                if (column.getRenderTextOnly()) {
                    if (!(element instanceof Component)) {
                        tdinnercontent.addInner("");
                    } else {
                        component = (Component)element;
                        text = component.getText();
                        tdinnercontent.addInner(text);
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
                    
                    tags.add(get(element.getType()).run(element, config));
                    if (mark)
                        table.setEnabled(savemark);
                    tdinnercontent.addChildren(tags);
                }
            }
            
            tdinner = new XMLElement("ul");
            tdinner.add("class", "td_inner");
            tdinner.addChild(tdinnerlabel);
            tdinner.addChild(tdinnercontent);
            
            tdtag.addChild(tdinner);
            trtag.addChild(tdtag);
        }
        
        return trtag;
    }
}
