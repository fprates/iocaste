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
    public static final byte ADD = 0;
    public static final byte REMOVE = 1;
    public static final byte ACCEPT = 2;
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    private String[] buttons;
    private Map<String, Column> columns;
    private Container container;
    private Table table;
    private View view;
    
    public TableTool(Container container, String name) {
        this.container = new StandardContainer(container, name.concat("cnt"));
        buttons = new String[] {
                "add".concat(name),
                "remove".concat(name),
                "accept".concat(name)
        };
        
        for (String button : buttons)
            new Button(this.container, button);
        
        table = new Table(this.container, name);
        table.setMark(true);
        table.setVisibleLines(15);
        columns = new HashMap<>();
        view = container.getView();
    }
    
    public final void accept() {
        Button[] buttons = getButtons(view);
        
        buttons[ACCEPT].setVisible(false);
        buttons[ADD].setVisible(true);
        buttons[REMOVE].setVisible(true);
        table.setTopLine(0);
    }
    
    public final void add() {
        Button[] buttons = getButtons(view);

        buttons[ACCEPT].setVisible(true);
        buttons[ADD].setVisible(false);
        buttons[REMOVE].setVisible(false);
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
    
    public final void clear() {
        table.clear();
    }
    
    public final void disable(String... tcolumns) {
        for (String name :  columns.keySet())
            columns.get(name).disabled = false;
        
        for (String column : tcolumns)
            columns.get(column).disabled = true;
        
        for (TableItem item : table.getItems())
            for (Element element : item.getElements())
                element.setEnabled(!columns.get(element.getName()).disabled);
    }
    
    public final String getButtonName(byte code) {
        return buttons[code];
    }
    
    private final Button[] getButtons(View view) {
        Button[] buttons = new Button[this.buttons.length];
        
        for (int i = 0; i < buttons.length; i++)
            buttons[i] = view.getElement(this.buttons[i]);
        
        return buttons;
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
    
    public final void setMode(byte mode, View view) {
        Button[] buttons = getButtons(view);
        
        switch (mode) {
        case UPDATE:
            buttons[ACCEPT].setVisible(false);
            buttons[ADD].setVisible(true);
            buttons[REMOVE].setVisible(true);
            table.setMark(true);
            break;
            
        case DISPLAY:
            buttons[ACCEPT].setVisible(false);
            buttons[ADD].setVisible(false);
            buttons[REMOVE].setVisible(false);
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
}

class ValidatorData {
    public Class<? extends Validator> validator;
    public String[] inputs;
}

class Column {
    public boolean disabled;
    public Const type;
    public ValidatorData validator;
}