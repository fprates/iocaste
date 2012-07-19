package org.iocaste.documents;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;

public class CDocument {

    /**
     * 
     * @param cdname
     * @param id
     * @param cache
     * @return
     * @throws Exception
     */
    public static final ComplexDocument get(String cdname, long id, Cache cache)
            throws Exception {
        ComplexModel model = CModel.get(cdname, cache);
        ComplexDocument document = new ComplexDocument(model);
        DocumentModel cdmodel = Model.get("COMPLEX_DOCUMENT", cache);
        ExtendedObject object = Query.get(cdmodel, id, cache.function);
        
        document.setId((Long)object.getValue("ID"));
        object = new ExtendedObject(model.getHeader());
        document.setHeader(object);
        
        return document;
    }
    
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
        String cd2doclink = object.getValue("CD_LINK");
        
        if (current == 0)
            current = cmodelid * 100000000;
        
        current++;
        object.setValue("CURRENT", current);
        if (Query.modify(object, cache.function) == 0)
            throw new IocasteException("error on complex model update");
        
        object = new ExtendedObject(docmodel);
        object.setValue("ID", current);
        object.setValue("COMPLEX_MODEL", cmodel.getName());
        if (Query.save(object, cache.function) == 0)
            throw new IocasteException("error on insert complex document");
        
        docmodel = Model.get(cd2doclink, cache);
        object = new ExtendedObject(docmodel);
        object.setValue("ID", current);
        if (Query.save(object, cache.function) == 0)
            throw new IocasteException("error on insert complex document link");
        
        return current - cmodelid;
    }
}
