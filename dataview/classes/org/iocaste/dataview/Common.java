package org.iocaste.dataview;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;

public class Common {
    public static final void addTableItem(Table table, ExtendedObject object) {
        Element tfield;
        String name;
        TableItem tableitem = new TableItem(table);
        DocumentModel model = table.getModel();
        
        for (DocumentModelItem modelitem : model.getItens()) {
            name = modelitem.getName();
            
            tfield = new TextField(table, name);
            tfield.setEnabled(!model.isKey(modelitem));
            
            tableitem.add(tfield);
        }
        
        tableitem.setObject(object);
    }
}
