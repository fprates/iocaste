package org.iocaste.appbuilder;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.CustomContainer;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
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
        export("validate", "validate");
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
    
    private final void additems(Table table, ExtendedObject[] items) {
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
    
    private final void initialize(CustomContainer component) {
        Map<String, Button> controls;
        String buttonname;
        Table table;
        DocumentModel model;
        ExtendedObject[] objects;
        String componentname = component.getName();
        Container container = new StandardContainer(
                component, componentname.concat("cnt"));
        
        controls = new HashMap<>();
        component.set("controls", controls);
        
        for (String name : new String[] {
                TableTool.ADD,
                TableTool.REMOVE,
                TableTool.ACCEPT}) {
            buttonname = name.concat(componentname);
            controls.put(name, new Button(container, buttonname));
        }

        switch (component.getb("mode")) {
        case TableTool.CONTINUOUS_UPDATE:
        case TableTool.UPDATE:
            controls.get(TableTool.ACCEPT).setVisible(false);
            controls.get(TableTool.ADD).setVisible(true);
            controls.get(TableTool.REMOVE).setVisible(true);
            break;
            
        case TableTool.DISPLAY:
            controls.get(TableTool.ACCEPT).setVisible(false);
            controls.get(TableTool.ADD).setVisible(false);
            controls.get(TableTool.REMOVE).setVisible(false);
            break;
        }
        
        model = new Documents(this).getModel(component.getst("model"));
        table = new Table(container, componentname.concat("_table"));
        table.setMark(component.getbl("mark"));
        table.setVisibleLines(component.geti("visible_lines"));
        table.importModel(model);
        table.setBorderStyle(component.getst("borderstyle"));
        table.setEnabled(component.getbl("enabled"));
        
        step = component.geti("step");
        itemcolumn = component.getst("itemcolumn");
        columns = component.get("columns");
        for (TableColumn column : table.getColumns())
            if (!column.isMark())
                column.setVisible((boolean)
                        columns.get(column.getName()).get("visible"));
        
        objects = component.get("objects");
        additems(table, objects);
    }
    
    private final void performTableAction(CustomContainer container,
            String action) {
        int i;
        byte mode;
        Map<String, Button> controls = container.get("controls");
        Table table = (Table)container.getView().getElement(
                container.getName().concat("_table"));
        
        switch (action) {
        case TableTool.ACCEPT:
            controls.get(TableTool.ACCEPT).setVisible(false);
            controls.get(TableTool.ADD).setVisible(true);
            controls.get(TableTool.REMOVE).setVisible(true);
            table.setTopLine(0);
            break;
        case TableTool.ADD:
            mode = container.getb("mode");
            i = 0;
            switch (mode) {
            case TableTool.CONTINUOUS_UPDATE:
                for (TableItem item_ : table.getItems()) {
                    if (!item_.isSelected()) {
                        i++;
                        continue;
                    }
                    break;
                }
                
                additem(table, null, i);
                break;
            default:
                controls.get(TableTool.ACCEPT).setVisible(true);
                controls.get(TableTool.ADD).setVisible(false);
                controls.get(TableTool.REMOVE).setVisible(false);
                additems(table, null);
                break;
            }
            break;
        case TableTool.REMOVE:
            for (TableItem item : table.getItems())
                if (item.isSelected())
                    table.remove(item);
            break;
        }
        
        container.set("action", null);
    }
    
    public final CustomContainer render(Message message) {
        String action;
        CustomContainer component = message.get("container");
        
        if (!component.isInitialized())
            initialize(component);
        
        action = component.getst("action");
        if (action != null)
            performTableAction(component, action);
        
        return component;
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
    public Map<String, Object> validate(Message message) {
        Map<String, Object> properties;
        TableItem[] items;
        ExtendedObject[] objects;
        CustomContainer container = message.get("container");
        Table table = container.getView().getElement(container.getName().
                concat("_table"));

        items = table.getItems();
        if (items.length == 0)
            return null;
        
        objects = new ExtendedObject[items.length];
        for (int i = 0; i < items.length; i++)
            objects[i] = items[i].getObject();

        properties = container.properties();
        properties.put("objects", objects);
        return properties;
    }
}