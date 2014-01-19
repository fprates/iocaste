package org.iocaste.documents;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;

public class Save {
    
    /**
     * 
     * @param object
     * @param function
     * @return
     */
    public static final int init(ExtendedObject object, Function function) {
        DocumentModel model = object.getModel();
        DocumentModelItem[] itens = model.getItens();
        int i = itens.length;
        Iocaste iocaste = new Iocaste(function);
        Object[] criteria = (i > 0)? new Object[i] : null;
        
        i = 0;
        for (DocumentModelItem item : model.getItens())
            criteria[i++] = object.get(item);
        
        return iocaste.update(model.getQuery("insert"), criteria);
    }
}
