package org.iocaste.shell.common;

import java.util.Locale;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public class ReportTool {
    private Table table;
    
    public ReportTool(Container container, String name) {
        table = new Table(container, name);
    }
    
    public final void setItens(ExtendedObject[] itens) {
        DataElement element;
        Component component;
        InputComponent input;
        String stext, name;
        TableItem item;
        TableColumn[] columns;
        Locale locale;
        Object value;
        
        if (itens == null || itens.length == 0)
            return;

        locale = table.getLocale();
        table.importModel(itens[0].getModel());
        columns = table.getColumns();
        for (ExtendedObject oitem : itens) {
            item = new TableItem(table);
            for (TableColumn column : columns) {
                if (column.isMark())
                    continue;

                name  = column.getName();
                value = oitem.getValue(name);
                element = column.getModelItem().getDataElement();
                switch (element.getType()) {
                case DataType.BOOLEAN:
                    input = new CheckBox(table, name);
                    input.set(value);
                    input.setEnabled(false);
                    item.add(input);
                    break;
                default:
                    stext = Shell.toString(value, element, locale, false);
                    component = new Text(table, name);
                    component.setText(stext);
                    item.add(component);
                    break;
                }
            }
        }
    }
    
    public final void visible(String... columns) {
        for (TableColumn column : table.getColumns())
            column.setVisible(false);
        
        for (String column : columns)
            table.getColumn(column).setVisible(true);
    }
}
