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
import org.iocaste.shell.common.View;

public class TableTool {
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String ACCEPT = "accept";
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;
    private Map<String, Control> controls;
    private Map<String, Column> columns;
    private Container container;
    private Table table;
    private View view;
    
    public TableTool(Container container, String name) {
        this.container = new StandardContainer(container, name.concat("cnt"));
        controls = new HashMap<>();
        controls.put(ADD, new Control(this.container, "add", name));
        controls.put(REMOVE, new Control(this.container, "remove", name));
        controls.put(ACCEPT, new Control(this.container, "accept", name));
        
        table = new Table(this.container, name);
        table.setMark(true);
        table.setVisibleLines(15);
        columns = new HashMap<>();
        view = container.getView();
    }
    
    public final void accept() {
        updateControlsView(view);
        
        controls.get(ACCEPT).setVisible(false);
        controls.get(ADD).setVisible(true);
        controls.get(REMOVE).setVisible(true);
        table.setTopLine(0);
    }
    
    public final void add() {
        updateControlsView(view);

        controls.get(ACCEPT).setVisible(true);
        controls.get(ADD).setVisible(false);
        controls.get(REMOVE).setVisible(false);
        additems();
    }
    
    public final void additems() {
        int total = table.length();
        
        for (int i = 0; i < table.getVisibleLines(); i++)
            additem(null);
        
        table.setTopLine(total);
    }
    
    public final void additem(ExtendedObject object) {
        Column column;
        DataElement element;
        InputComponent input;
        String name;
        TableItem item = new TableItem(table);
        
        for (TableColumn tcolumn : table.getColumns()) {
            if (tcolumn.isMark())
                continue;

            name = tcolumn.getName();
            column = columns.get(name);
            element = tcolumn.getModelItem().getDataElement();
            switch (element.getType()) {
            case DataType.BOOLEAN:
                input = new CheckBox(table, name);
                break;
            default:
                switch (column.type) {
                case LIST_BOX:
                    input = new ListBox(table, name);
                    if (column.values == null)
                        break;
                    
                    for (String vname : column.values.keySet())
                        ((ListBox)input).add(vname, column.values.get(vname));
                    break;
                default:
                    input = new TextField(table, name);
                    break;
                }
                
                if (column.validator == null)
                    break;
                
                input.setValidator(column.validator.validator);
                if (column.validator.inputs == null)
                    break;
                
                for (String vinputname : column.validator.inputs)
                    input.addValidatorInput(
                            (InputComponent)item.get(vinputname));
                break;
            }
            
            if (column.disabled)
                input.setEnabled(false);
            
            item.add(input);
        }
        
        if (object == null)
            return;
        
        item.setObject(object);
    }
    
    public final void controls(byte state, String... controls) {
        switch (state) {
        case ENABLED:
        case DISABLED:
            if ((controls == null) || (controls.length == 0)) {
                for (String control : this.controls.keySet())
                    this.controls.get(control).setVisible(state == ENABLED);
                break;
            }
            
            for (String control : controls)
                this.controls.get(control).setVisible(state == ENABLED);
            break;
        }
    }
    
    public final void clear() {
        table.clear();
    }
    
    public final void disable(String... tcolumns) {
        String name;
        
        for (String cname :  columns.keySet())
            columns.get(cname).disabled = false;
        
        for (String column : tcolumns)
            columns.get(column).disabled = true;
        
        for (TableItem item : table.getItems())
            for (TableColumn column : table.getColumns()) {
                if (column.isMark())
                    continue;
                
                name = column.getName();
                item.get(name).setEnabled(!columns.get(name).disabled);
            }
    }
    
    public final Container getContainer() {
        return container;
    }
    
    public final TableItem[] getItems() {
        return table.getItems();
    }
    
    public final void model(DocumentModel model) {
        Column column;
        
        table.importModel(model);
        columns.clear();
        for (TableColumn tcolumn : table.getColumns()) {
            column = new Column();
            column.type = Const.TEXT_FIELD;
            columns.put(tcolumn.getName(), column);
        }
    }
    
    public final void refresh(View view) {
        table = view.getElement(this.table.getName());
        this.view = view;
    }
    
    public final void remove() {
        removeitems(table);
    }
    
    public final void setColumnType(String column, Const type) {
        columns.get(column).type = type;
    }
    
    public final void setColumnValues(String column, Map<String, Object> values)
    {
        columns.get(column).values = values;
    }
    
    public final void setMode(byte mode, View view) {
        updateControlsView(view);
        
        switch (mode) {
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
            
            for (TableItem item : table.getItems())
                for (Element element : item.getElements())
                    element.setEnabled(false);
            break;
        }
    }
    
    public final void setObjects(ExtendedObject[] objects) {
        if (objects == null || objects.length == 0)
            additems();
        else
            for (ExtendedObject object : objects)
                additem(object);
    }
    
    public final void setValidator(String field,
            Class<? extends Validator> validator, String... inputs) {
        ValidatorData vdata = new ValidatorData();
        
        vdata.validator = validator;
        vdata.inputs = inputs;
        columns.get(field).validator = vdata;
    }
    
    public final void setVisibility(boolean visible, String... columns) {
        for (TableColumn column : table.getColumns())
            if (!column.isMark())
                column.setVisible(!visible);
        
        for (String column : columns)
            table.getColumn(column).setVisible(visible);
    }
    
    public final void setVisibleLines(int lines) {
        table.setVisibleLines(lines);
    }
    
    public static final void removeitems(Table table) {
        for (TableItem item : table.getItems())
            if (item.isSelected())
                table.remove(item);
    }
    
    private final void updateControlsView(View view) {
        for (String name : controls.keySet())
            controls.get(name).setView(view);
    }
}

class Control {
    private Button button;
    
    public Control(Container container, String name, String tname) {
        button = new Button(container, name.concat(tname));
    }
    
    public final void setView(View view) {
        button = view.getElement(button.getName());
    }
    
    public final void setVisible(boolean visible) {
        button.setVisible(visible);
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
}