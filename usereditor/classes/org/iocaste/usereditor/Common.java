package org.iocaste.usereditor;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;

public class Common {
    public static final byte CREATE = 0;
    public static final byte DISPLAY = 1;
    public static final byte UPDATE = 2;
    public static final String[] TITLE = {
        "usereditor-create",
        "usereditor-display",
        "usereditor-update"
    };
    
    public static final byte getMode(View view) {
        return view.getParameter("mode");
    }
    
    public static final void insertItem(Table itens, ExtendedObject object,
            byte mode) {
        InputComponent input;
        TableItem item = new TableItem(itens);
        
        for (TableColumn column : itens.getColumns()) {
            if (column.isMark())
                continue;
            
            input = new TextField(itens, column.getName());
            input.setEnabled(mode != Common.DISPLAY);
            item.add(input);
            if (input.getName().equals("PROFILE"))
                input.getModelItem().setSearchHelp("SH_USER_PROFILE");
        }
        
        if (object == null)
            return;
        
        item.setObject(object);
    }
    
    public static final void removeItem(Table table) {
        for (TableItem item : table.getItens())
            if (item.isSelected())
                table.remove(item);
    }
}
