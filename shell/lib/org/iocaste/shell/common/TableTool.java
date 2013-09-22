package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
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
    private Set<String> disabled;
    private Container container;
    private Table table;
    private View view;
    private Map<String, ValidatorData> validators;
    
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
        disabled = new HashSet<>();
        view = container.getView();
        validators = new HashMap<>();
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
        DataElement element;
        InputComponent input;
        String name;
        ValidatorData vdata;
        Class<? extends Validator> validator;
        TableItem item = new TableItem(table);
        
        for (TableColumn column : table.getColumns()) {
            if (column.isMark())
                continue;

            name = column.getName();
            element = column.getModelItem().getDataElement();
            switch (element.getType()) {
            case DataType.BOOLEAN:
                input = new CheckBox(table, name);
                break;
            default:
                input = new TextField(table, name);
                if (!validators.containsKey(name))
                    break;
                
                validator = validators.get(name).validator;
                if (validator != null)
                    input.setValidator(validator);
                
                break;
            }
            
            if (disabled.contains(name))
                input.setEnabled(false);
            
            item.add(input);
        }
        
        for (String vname : validators.keySet()) {
            input = item.get(vname);
            vdata = validators.get(vname);
            if (vdata.inputs == null)
                continue;
            
            for (String vinputname : vdata.inputs)
                input.addValidatorInput((InputComponent)item.get(vinputname));
        }
        
        if (object == null)
            return;
        
        item.setObject(object);
    }
    
    public final void disable(String... columns) {
        disabled.clear();
        for (String column : columns)
            disabled.add(column);
        
        for (TableItem item : table.getItems())
            for (Element element : item.getElements())
                element.setEnabled(!disabled.contains(element.getName()));
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
    
    public final Table getTable() {
        return table;
    }
    
    public final void refresh(View view) {
        table = view.getElement(this.table.getName());
        this.view = view;
    }
    
    public final void remove() {
        removeitems(table);
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
        validators.put(field, vdata);
    }
    
    public final void setVisibility(boolean visible, String... columns) {
        for (TableColumn column : table.getColumns())
            if (!column.isMark())
                column.setVisible(!visible);
        
        for (String column : columns)
            table.getColumn(column).setVisible(visible);
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