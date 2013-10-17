package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public class TableTool {
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String ACCEPT = "accept";
    public static final byte CONTINUOUS_UPDATE = 0;
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;
    private byte mode;
    private Map<String, Control> controls;
    private Map<String, Column> columns;
    private Container container;
    private String tablename;
    private PageContext context;
    
    public TableTool(Container container, String name) {
        Table table;
        
        this.container = new StandardContainer(container, name.concat("cnt"));
        controls = new HashMap<>();
        controls.put(ADD, new Control(this.container, "add", name));
        controls.put(REMOVE, new Control(this.container, "remove", name));
        controls.put(ACCEPT, new Control(this.container, "accept", name));
        
        tablename = name;
        table = new Table(this.container, tablename);
        table.setMark(true);
        table.setVisibleLines(15);
        columns = new HashMap<>();
    }
    
    public final void accept() {
        Table table = context.view.getElement(tablename);
        
        controls.get(ACCEPT).setVisible(false);
        controls.get(ADD).setVisible(true);
        controls.get(REMOVE).setVisible(true);
        table.setTopLine(0);
    }
    
    public final void add() {
        int i = 0;
        Table table = context.view.getElement(tablename);
        
        switch (mode) {
        case CONTINUOUS_UPDATE:
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
            controls.get(ACCEPT).setVisible(true);
            controls.get(ADD).setVisible(false);
            controls.get(REMOVE).setVisible(false);
            additems(table, null);
            break;
        }
    }
    
    public final void additems() {
        Table table = context.view.getElement(tablename);
        
        additems(table, null);
    }
    
    private final void additems(Table table, ExtendedObject[] items) {
        int vlines = table.getVisibleLines();
        int total = table.length();
        
        if (items == null) {
            for (int i = 0; i < vlines; i++)
                additem(table, null, -1);
        } else {
            for (int i = 0; i < items.length; i++) {
                if (vlines == items.length)
                    break;
                
                additem(table, items[i], -1);
            }
        }
        
        table.setTopLine(total);
    }
    
    public final void additem(ExtendedObject object) {
        Table table = context.view.getElement(tablename);
        
        additem(table, object, -1);
    }
    
    private void additem(Table table, ExtendedObject object, int pos) {
        Column column;
        Element element;
        DataElement delement;
        InputComponent input;
        String name;
        TableItem item = new TableItem(table, pos);
        TableColumn[] tcolumns = table.getColumns();
        
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
                switch (column.type) {
                case TEXT:
                    element = new Text(table, name);
                    break;
                case LIST_BOX:
                    input = new ListBox(table, name);
                    element = input;
                    if (column.values == null)
                        break;
                    
                    for (String vname : column.values.keySet())
                        ((ListBox)input).add(vname, column.values.get(vname));
                    break;
                case TEXT_FIELD:
                    element = new TextField(table, name);
                    break;
                case LINK:
                    element = new Link(table, name, column.action);
                    break;
                default:
                    throw new RuntimeException("component type not supported"
                            + " in this version.");
                }
            }
            
            if (column.disabled)
                element.setEnabled(false);
            
            item.add(element);
        }
        
        /*
         * só podemos tratar os validadores quando todos os
         * componentes de entrada estiverem definidos.
         * por isso não podemos tratar no loop anterior.
         */
        for (TableColumn tcolumn : tcolumns) {
            if (tcolumn.isMark())
                continue;
            
            name = tcolumn.getName();
            element = item.get(name);
            if (!element.isDataStorable())
                continue;
            
            column = columns.get(name);
            if (column.validator == null)
                continue;
            
            input = (InputComponent)element; 
            input.setValidator(column.validator.validator);
            if (column.validator.inputs == null)
                continue;
            
            for (String vinputname : column.validator.inputs)
                input.addValidatorInput((InputComponent)item.get(vinputname));
        }
        
        if (object == null)
            return;
        
        item.setObject(object);
    }
    
    public final void controls(byte state, String... controls) {
        Control control;
        Table table = context.view.getElement(tablename);
        
        switch (state) {
        case ENABLED:
        case DISABLED:
            if ((controls == null) || (controls.length == 0)) {
                for (String name : this.controls.keySet())
                    this.controls.get(name).setVisible(state == ENABLED);
                
                table.setMark(state == ENABLED);
                break;
            }
            
            for (String name : controls) {
                control = this.controls.get(name);
                if (control == null)
                    throw new RuntimeException(name.
                            concat(" is an invalid control."));
                
                control.setVisible(state == ENABLED);
            }
            break;
        }
    }
    
    public final void clear() {
        Table table = context.view.getElement(tablename);
        
        table.clear();
    }
    
    public final Container getContainer() {
        return container;
    }
    
    public final TableItem[] getItems() {
        Table table = context.view.getElement(tablename);
        
        return table.getItems();
    }
    
    public final void model(DocumentModel model) {
        Column column;
        Table table = context.view.getElement(tablename);
        
        table.importModel(model);
        columns.clear();
        for (TableColumn tcolumn : table.getColumns()) {
            if (tcolumn.isMark())
                continue;
            
            column = new Column();
            column.type = Const.TEXT_FIELD;
            column.tcolumn = tcolumn;
            columns.put(tcolumn.getName(), column);
        }
    }
    
    public final void remove() {
        Table table = context.view.getElement(tablename);
        
        for (TableItem item : table.getItems())
            if (item.isSelected())
                table.remove(item);
    }
    
    public final void setColumnSize(String column, int size) {
        columns.get(column).tcolumn.setLength(size);
    }
    
    public final void setColumnType(String name, Const type) {
        setColumnType(name, type, null);
    }
    
    public final void setColumnType(String name, Const type, String action) {
        Column column = columns.get(name);
        
        column.type = type;
        column.action = action;
    }
    
    public final void setColumnStatus(byte status, String... tcolumns) {
        String name;
        Column column;
        Table table = context.view.getElement(tablename);
        
        if (tcolumns == null || tcolumns.length == 0) {
            for (String cname :  columns.keySet())
                columns.get(cname).disabled = (status == DISABLED);
        } else {
            for (String cname : tcolumns) {
                column = columns.get(cname);
                if (column == null)
                    throw new RuntimeException(cname.concat(
                            " is an invalid column."));
                
                column.disabled = (status == DISABLED);
            }
        }
        
        for (TableItem item : table.getItems())
            for (TableColumn tcolumn : table.getColumns()) {
                if (tcolumn.isMark())
                    continue;
                
                name = tcolumn.getName();
                item.get(name).setEnabled(!columns.get(name).disabled);
            }
    }
    
    public final void setColumnValues(String name, Map<String, Object> values)
    {
        columns.get(name).values = values;
    }
    
    /**
     * 
     * @param context
     */
    public final void setContext(PageContext context) {
        this.context = context;
        for (String name : controls.keySet())
            controls.get(name).setContext(context);
    }
    
    public final void setMode(byte mode) {
        Table table = context.view.getElement(tablename);
        
        this.mode = mode;
        switch (mode) {
        case CONTINUOUS_UPDATE:
        case UPDATE:
            controls.get(ACCEPT).setVisible(false);
            controls.get(ADD).setVisible(true);
            controls.get(REMOVE).setVisible(true);
            table.setMark(true);
            break;
            
        case DISPLAY:
            controls.get(ACCEPT).setVisible(false);
            controls.get(ADD).setVisible(false);
            controls.get(REMOVE).setVisible(false);
            table.setMark(false);
            break;
        }
        
        for (TableItem item : table.getItems())
            for (Element element : item.getElements())
                element.setEnabled(mode != DISPLAY);
        
        for (String column : columns.keySet())
            columns.get(column).disabled = (mode == DISPLAY);
    }
    
    public final void setObjects(ExtendedObject[] objects) {
        Table table = context.view.getElement(tablename);
        
        if (objects == null || objects.length == 0)
            additems(table, null);
        else
            additems(table, objects);
    }
    
    public final void setValidator(String field,
            Class<? extends Validator> validator, String... inputs) {
        ValidatorData vdata = new ValidatorData();
        
        vdata.validator = validator;
        vdata.inputs = inputs;
        columns.get(field).validator = vdata;
    }
    
    public final void setVisibility(boolean visible, String... columns) {
        Table table = context.view.getElement(tablename);
        
        for (TableColumn column : table.getColumns())
            if (!column.isMark())
                column.setVisible(!visible);
        
        for (String column : columns)
            table.getColumn(column).setVisible(visible);
    }
    
    public final void setVisibleLines(int lines) {
        Table table = context.view.getElement(tablename);
        
        table.setVisibleLines(lines);
    }
}

class Control {
    private String buttonname;
    private PageContext context;
    
    public Control(Container container, String name, String tname) {
        buttonname = name.concat(tname);
        new Button(container, buttonname);
    }
    
    public final void setContext(PageContext context) {
        this.context = context;
    }
    
    public final void setVisible(boolean visible) {
        context.view.getElement(buttonname).setVisible(visible);
    }
}

class ValidatorData {
    public Class<? extends Validator> validator;
    public String[] inputs;
}

class Column {
    public boolean disabled;
    public Const type;
    public ValidatorData validator;
    public Map<String, Object> values;
    public TableColumn tcolumn;
    public String action;
}