package org.iocaste.appbuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.CustomComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;

public class ComponentRender extends AbstractFunction {
    private int step;
    private Map<String, Map<String, Object>> columns;
    private String itemcolumn;
    
    public ComponentRender() {
        export("render", "render");
    }
    
    @SuppressWarnings("unchecked")
    private void additem(Table table, ExtendedObject object, int pos) {
        int last;
        Map<String, Object> column;
        Element element;
        DataElement delement;
        InputComponent input;
        String name;
        Map<String, String> values;
        TableItem item = new TableItem(table, pos);
        TableColumn[] tcolumns = table.getColumns();
        
        last = 0;
        for (TableColumn tcolumn : tcolumns) {
            if (tcolumn.isMark())
                continue;

            name = tcolumn.getName();
            column = columns.get(name);
            delement = tcolumn.getModelItem().getDataElement();
            switch (delement.getType()) {
            case DataType.BOOLEAN:
                element = new CheckBox(table, name);
                break;
            default:
                switch ((Const)column.get("type")) {
                case TEXT:
                    element = new Text(table, name);
                    break;
                case LIST_BOX:
                    input = new ListBox(table, name);
                    element = input;
                    values = (Map<String, String>)column.get("values");
                    if (values == null)
                        break;
                    
                    for (String vname : values.keySet())
                        ((ListBox)input).add(vname, values.get(vname));
                    break;
                case TEXT_FIELD:
                    element = new TextField(table, name);
                    break;
                case LINK:
                    element = new Link(
                            table, name, (String)column.get("action"));
                    break;
                default:
                    throw new RuntimeException("component type not supported"
                            + " in this version.");
                }
            }
            
            if (object == null && itemcolumn != null && name.
                    equals(itemcolumn)) {
                last += step;
                if (element.isDataStorable()) {
                    input = (InputComponent)element;
                    input.set(last);
                } else {
                    ((Text)element).setText(Long.toString(last));
                }
            }
            
            element.setEnabled(!((boolean)column.get("disabled")));
            item.add(element);
        }
        
        /*
         * só podemos tratar os validadores quando todos os
         * componentes de entrada estiverem definidos.
         * por isso não podemos tratar no loop anterior.
         */
//        for (TableColumn tcolumn : tcolumns)
//            setColumnValidator(tcolumn, item);
        
        if (object == null)
            return;
        
        if (itemcolumn != null) {
            last += step; 
            object.set(itemcolumn, last);
        }
        
        item.setObject(object);
    }
    
    private final void additems(Table table, Collection<ExtendedObject> items) {
        int i = 0;
        int vlines = table.getVisibleLines();
        int total = table.length();
        
        if (items == null) {
            if (vlines == 0)
                vlines = 15;
            
            for (int k = 0; k < vlines; k++)
                additem(table, null, -1);
        } else {
            i = -1;
            for (ExtendedObject item : items) {
                i++;
                if ((vlines == i) && (vlines > 0))
                    break;
                
                additem(table, item, -1);
            }
        }
        
        table.setTopLine(total);
    }
    
    @SuppressWarnings("unchecked")
    public final XMLElement render(Message message) {
        DocumentModel model;
        Table table;
        String buttonname, componentname;
        Container container;
        Collection<ExtendedObject> objects;
        CustomComponent component = message.get("element");
        
        componentname = component.getName();
        container = new StandardContainer(
                component.getContainer(), componentname.concat("cnt"));
        for (String name : new String[] {"add", "remove", "accept"}) {
            buttonname = name.concat(componentname);
            new Button(container, buttonname);
        }
        
        model = (DocumentModel)component.get("model");
        table = new Table(container, componentname);
        table.setMark(component.getbl("mark"));
        table.setVisibleLines(component.geti("visible_lines"));
        table.importModel(model);
        
        step = component.geti("step"); 
        itemcolumn = component.getst("itemcolumn");
        objects = (List<ExtendedObject>)component.get("objects");
        additems(table, objects);
        
        return new Shell(this).render(container).get(0);
    }
    
//    /**
//     * 
//     * @param tcolumn
//     * @param item
//     */
//    private final void setColumnValidator(TableColumn tcolumn, TableItem item) {
//        InputComponent input;
//        Map<String, Object> column;
//        Element element;
//        String name;
//        
//        if (tcolumn.isMark())
//            return;
//        
//        name = tcolumn.getName();
//        element = item.get(name);
//        if (!element.isDataStorable())
//            return;
//        
//        column = columns.get(name);
//        if (column.get("validator") == null)
//            return;
//        
//        input = (InputComponent)element; 
//        input.setValidator(column.validator.validator);
//        if (column.validator.inputs == null)
//            return;
//        
//        for (String vinputname : column.validator.inputs)
//            input.addValidatorInput((InputComponent)item.get(vinputname));
//    }
}