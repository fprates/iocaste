package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;

public class ReportTool {
    private Table table;
    
    public ReportTool(Container container, String name) {
        table = new Table(container, name);
    }
    
    public final void setItens(ExtendedObject[] itens) {
        String stext;
        TableItem item;
        TableColumn[] columns;
        DocumentModelItem mitem;
        Text text;
        
        if (itens == null || itens.length == 0)
            return;

        table.importModel(itens[0].getModel());
        columns = table.getColumns();
        for (ExtendedObject oitem : itens) {
            item = new TableItem(table);
            for (TableColumn column : columns) {
                if (column.isMark())
                    continue;
                
                mitem = column.getModelItem();
                stext = Shell.toString(oitem.getValue(mitem),
                        mitem.getDataElement(), table.getLocale(), false);
                text = new Text(table, column.getName());
                text.setText(stext);
                item.add(text);
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
