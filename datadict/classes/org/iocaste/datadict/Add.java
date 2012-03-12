package org.iocaste.datadict;

import java.util.Map;

import org.iocaste.datadict.Common.ItensNames;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Add {
    
    /**
     * 
     * @param itens
     */
    public static void insertSHItem(Table itens) {
        TextField tfield;
        TableItem item = new TableItem(itens);
        DocumentModel model = itens.getModel();
        
        for (DocumentModelItem modelitem : model.getItens()) {
            tfield = new TextField(itens, modelitem.getName());
            tfield.setModelItem(modelitem);
            
            item.add(tfield);
        }
    }

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        byte mode = Common.getMode(view);
        Table itens = view.getElement("itens");
        Map<ItensNames, DataElement> references =
                Common.getFieldReferences(function);
        
        if (Common.hasItemDuplicated(view))
            return;
        
        Common.insertItem(itens, mode, null, references);
    }
    
    /**
     * 
     * @param view
     */
    public static final void shitem(ViewData view) {
        Table itens = view.getElement("itens");
        
        insertSHItem(itens);
    }
}
