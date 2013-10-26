package org.iocaste.documents;

import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;

public class Delete {

    public static final int init(Iocaste iocaste, ExtendedObject object) {
        int i = 0;
        DocumentModel model = object.getModel();
        Set<DocumentModelKey> keys = model.getKeys();
        Object[] criteria = new Object[keys.size()];
        
        for (DocumentModelKey key : keys)
            criteria[i++] = object.get(model.getModelItem(
                    key.getModelItemName()));
        
        return iocaste.update(model.getQuery("delete"), criteria);
    }
}
