package org.iocaste.appbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class TableToolRenderer extends AbstractFunction {
    private int step, last;
    private Map<String, Map<String, Object>> columns;
    private String itemcolumn;
    private Table table;
    
    public TableToolRenderer() {
        export("render", "render");
        export("validate", "validate");
    }
    
    @SuppressWarnings("unchecked")
    private void additem(ExtendedObject object, int pos) {
        Map<String, Object> column;
        Element element;
        DataElement delement;
        InputComponent input;
        String name;
        Map<String, String> values;
        TableItem item = new TableItem(table, pos);
        TableColumn[] tcolumns = table.getColumns();
        
        for (TableColumn tcolumn : tcolumns) {
            if (tcolumn.isMark())
                continue;

            name = tcolumn.getName();
            column = columns.get(name);
            delement = tcolumn.getModelItem().getDataElement();
            if (column.containsKey("size"))
                tcolumn.setLength((int)column.get("size"));
            
            switch (delement.getType()) {
            case DataType.BOOLEAN:
                element = new CheckBox(item, name);
                break;
            default:
                switch ((Const)column.get("type")) {
                case TEXT:
                    element = new Text(item, name);
                    break;
                case LIST_BOX:
                    input = new ListBox(item, name);
                    element = input;
                    values = (Map<String, String>)column.get("values");
                    if (values == null)
                        break;
                    
                    for (String vname : values.keySet())
                        ((ListBox)input).add(vname, values.get(vname));
                    break;
                case TEXT_FIELD:
                    element = new TextField(item, name);
                    break;
                case LINK:
                    element = new Link(
                            item, name, (String)column.get("action"));
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
        }
        
        if (object == null)
            return;
        
        if (itemcolumn != null) {
            last += step; 
            object.set(itemcolumn, last);
        }
        
        item.setObject(object);
    }
    
    /**
     * 
     * @param table
     * @param items
     */
    private final void additems(Table table, ExtendedObject[] items) {
        int i = 0;
        int vlines = table.getVisibleLines();
        int total = table.size();
        
        if (items == null) {
            if (vlines == 0)
                vlines = 15;
            
            for (int k = 0; k < vlines; k++)
                additem(null, -1);
        } else {
            i = -1;
            for (ExtendedObject item : items) {
                i++;
                if ((vlines == i) && (vlines > 0))
                    break;
                
                additem(item, -1);
            }
        }
        
        table.setTopLine(total);
    }
    
    /**
     * 
     * @param container
     * @return
     */
    private final ExtendedObject[] getObjects(CustomContainer container) {
        int i;
        ExtendedObject[] objects;
        int length = table.size();
        
        if (length == 0)
            return null;

        i = 0;
        objects = new ExtendedObject[length];
        for (TableItem item : table.getItems())
            objects[i++] = item.getObject();
        
        return objects;
    }
    
    /**
     * 
     * @param custom
     */
    private final void initialize(CustomContainer custom) {
        Map<String, Button> controls;
        Map<String, Object> columnp;
        String _name;
        DocumentModel model;
        ExtendedObject[] objects;
        String componentname = custom.getName();
        Container container = new StandardContainer(
                custom, componentname.concat("cnt"));
        
        controls = new HashMap<>();
        custom.set("buttons", controls);
        
        for (String name : new String[] {
                TableTool.ADD,
                TableTool.REMOVE,
                TableTool.ACCEPT}) {
            _name = name.concat(componentname);
            controls.put(name, new Button(container, _name));
        }

        switch (custom.getb("mode")) {
        case TableTool.CONTINUOUS_UPDATE:
        case TableTool.UPDATE:
            controls.get(TableTool.ACCEPT).setVisible(false);
            controls.get(TableTool.ADD).setVisible(true);
            controls.get(TableTool.REMOVE).setVisible(true);
            break;
            
        case TableTool.DISPLAY:
        case TableTool.DONT_APPEND:
            controls.get(TableTool.ACCEPT).setVisible(false);
            controls.get(TableTool.ADD).setVisible(false);
            controls.get(TableTool.REMOVE).setVisible(false);
            break;
        }
        
        model = new Documents(this).getModel(custom.getst("model"));
        table = new Table(container, componentname.concat("_table"));
        table.setMark(custom.getbl("mark"));
        table.setVisibleLines(custom.geti("visible_lines"));
        table.importModel(model);
        table.setBorderStyle(custom.getst("borderstyle"));
        table.setEnabled(custom.getbl("enabled"));
        
        for (TableColumn column : table.getColumns()) {
            if (column.isMark())
                continue;
            _name = column.getName();
            columnp = columns.get(_name);
            column.setVisible((boolean)columnp.get("visible"));
            model.getModelItem(_name).setSearchHelp((String)columnp.get("sh"));
        }
        
        objects = custom.get("objects");
        additems(table, objects);
    }
    
    /**
     * 
     * @param container
     */
    private final void installValidators(CustomContainer container) {
        Map<String, Object> properties;
        
        for (TableItem item : table.getItems())
            for (TableColumn column : table.getColumns()) {
                if (column.isMark())
                    continue;
                properties = columns.get(column.getName());
                if (properties == null)
                    continue;
                setColumnValidator(properties, column, item);
            }
    }
    
    /**
     * 
     * @param container
     * @param action
     */
    private final void performTableAction(CustomContainer container,
            String action) {
        int i;
        byte mode;
        Map<String, Button> controls = container.get("buttons");
        
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
                
                additem(null, i);
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
    
    /**
     * 
     * @param message
     * @return
     */
    public final CustomContainer render(Message message) {
        ExtendedObject[] objects;
        String action;
        CustomContainer custom = message.get("container");

        step = custom.geti("step");
        itemcolumn = custom.getst("itemcolumn");
        columns = custom.get("columns");
        last = custom.geti("last");
        
        if (!custom.isInitialized())
            initialize(custom);
        else
            updateItems(custom);
        
        action = custom.getst("action");
        if (action != null) {
            performTableAction(custom, action);
            objects = getObjects(custom);
            custom.set("objects", objects);
            custom.set("selected", null);
        }
        
        installValidators(custom);
        custom.set("last", last);
        
        return custom;
    }
    
    /**
     * 
     * @param properties
     * @param tcolumn
     * @param item
     */
    private final void setColumnValidator(Map<String, Object> properties,
            TableColumn tcolumn, TableItem item) {
        Element element;
        String name;
        
        if (tcolumn.isMark())
            return;
        
        name = tcolumn.getName();
        element = item.get(name);
        if (!element.isDataStorable())
            return;
        
        name = (String)properties.get("validator");
        if (name == null)
            return;
        
        ((InputComponent)element).setValidator(name);
    }
    
    /**
     * 
     * @param custom
     */
    private void updateItems(CustomContainer custom) {
        int i;
        ExtendedObject[] objects = custom.get("objects");
        
        table = custom.getView().getElement(custom.getName().concat("_table"));
        if (objects.length == table.size()) {
            i = 0;
            for (TableItem item : table.getItems())
                item.setObject(objects[i++]);
        } else {
            table.clear();
            additems(table, objects);
        }
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public Map<String, Object> validate(Message message) {
        List<ExtendedObject> selected;
        Map<String, Object> properties;
        ExtendedObject[] objects;
        CustomContainer container = message.get("container");
        
        table = container.getView().getElement(container.getName().
                concat("_table"));

        properties = container.properties();
        if (table.size() == 0) {
            properties.put("objects", null);
            properties.put("selected", null);
            return null;
        }

        objects = getObjects(container);
        properties.put("objects", objects);
        selected = new ArrayList<>();
        for (TableItem item : table.getItems())
            if (item.isSelected())
                selected.add(item.getObject());

        properties.put("selected", (selected.size() == 0)?
                null : selected.toArray(new ExtendedObject[0]));
        
        return properties;
    }
}