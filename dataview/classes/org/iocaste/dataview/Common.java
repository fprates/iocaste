package org.iocaste.dataview;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;

public class Common {
    public static final void addTableItem(Table table, ExtendedObject object) {
        InputComponent input;
        String name;
        DataElement dataelement;
        TableItem tableitem = new TableItem(table);
        DocumentModel model = table.getModel();
        
        for (DocumentModelItem modelitem : model.getItens()) {
            name = modelitem.getName();
            
            dataelement = modelitem.getDataElement();
            switch (dataelement.getType()) {
            case DataType.BOOLEAN:
                input = new CheckBox(table, name);
                break;
            default:
                input = new TextField(table, name);
                break;
            }
            
            input.setEnabled(!model.isKey(modelitem));
            
            tableitem.add(input);
        }
        
        tableitem.setObject(object);
    }
}
