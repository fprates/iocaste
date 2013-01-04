package org.iocaste.shell.common;

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
    private Container container;
    private Table table;
    
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
    }
    
    public final void accept(View view) {
        Button[] buttons = getButtons(view);
        
        refresh(view);
        buttons[ACCEPT].setVisible(false);
        buttons[ADD].setVisible(true);
        buttons[REMOVE].setVisible(true);
        table.setTopLine(0);
    }
    
    public final void add(View view) {
        Button[] buttons = getButtons(view);

        refresh(view);
        buttons[ACCEPT].setVisible(true);
        buttons[ADD].setVisible(false);
        buttons[REMOVE].setVisible(false);
        additems(table);
    }
    
    public static final void additems(Table table) {
        int total = table.length();
        
        for (int i = 0; i < table.getVisibleLines(); i++)
            additem(table, null);
        
        table.setTopLine(total);
    }
    
    public static final void additem(Table table, ExtendedObject object) {
        TableItem item = new TableItem(table);
        
        for (TableColumn column : table.getColumns()) {
            if (column.isMark())
                continue;
            
            item.add(new TextField(table, column.getName()));
        }
        
        if (object == null)
            return;
        
        item.setObject(object);
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
    
    public final Table getTable() {
        return table;
    }
    
    public final void refresh(View view) {
        table = view.getElement(this.table.getName());
    }
    
    public final void remove(View view) {
        refresh(view);
        removeitems(table);
    }
    
    public final void setMode(byte mode, View view) {
        Button[] buttons = getButtons(view);
        
        refresh(view);
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
            
            for (TableItem item : table.getItens())
                for (Element element : item.getElements())
                    element.setEnabled(false);
            break;
        }
        
    }
    
    public final void visible(String... columns) {
        for (TableColumn column : table.getColumns())
            if (!column.isMark())
                column.setVisible(false);
        
        for (String column : columns)
            table.getColumn(column).setVisible(true);
    }
    
    public static final void removeitems(Table table) {
        for (TableItem item : table.getItens())
            if (item.isSelected())
                table.remove(item);
    }
    
    public static final void setObjects(Table table, ExtendedObject[] objects) {
        if (objects == null || objects.length == 0)
            additems(table);
        else
            for (ExtendedObject object : objects)
                additem(table, object);
    }
}
