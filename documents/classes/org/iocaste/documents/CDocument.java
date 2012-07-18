package org.iocaste.documents;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;

public class CDocument {

    /**
     * 
     * @param document
     * @param cache
     * @return
     * @throws Exception
     */
    public static final long save(ComplexDocument document, Cache cache)
            throws Exception {
        DocumentModel docmodel = Model.get("COMPLEX_DOCUMENT", cache);
        ComplexModel cmodel = document.getModel();
        String cmodelname = cmodel.getName();
        DocumentModel cmmodel = Model.get("COMPLEX_MODEL", cache);
        ExtendedObject object = Query.get(cmmodel, cmodelname, cache.function);
        long current = object.getValue("CURRENT");
        int cmodelid = object.getValue("ID");
        
        if (current == 0)
            current = cmodelid * 10000000;
        
        current++;
        object.setValue("CURRENT", current);
        if (Query.modify(object, cache.function) == 0)
            throw new IocasteException("error on complex model update");
        
        object = new ExtendedObject(docmodel);
        object.setValue("ID", current);
        object.setValue("COMPLEX_MODEL", cmodel.getName());
        if (Query.save(object, cache.function) == 0)
            throw new IocasteException("error on insert complex document");
        
        return current - cmodelid;
    }
}
