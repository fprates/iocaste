package org.iocaste.documents;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;

public class CDocument {
    private static final long HMULTIPLIER = 100000000;
    private static final int IMULTIPLIER = 100000;
    private static final byte COMPLEX_MODEL = 0;
    private static final byte COMPLEX_DOCUMENT = 1;
    private static final byte LINK = 2;
    private static final byte CD_ITENS = 0;
    private static final byte CM_ITENS = 1;
    private static final String[] QUERIES = {
        "from COMPLEX_DOCUMENT_ITEM where COMPLEX_DOCUMENT = ?",
        "from COMPLEX_MODEL_ITEM where COMPLEX_MODEL = ?"
    };
    
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
        ExtendedObject[] objects, cditens;
        long key, docid;
        ComplexDocument document;
        String name;
        DocumentModel model;
        
        document = getHeader(cdname, id, cache);
        if (document == null)
            return null;
        
        objects = Query.select(QUERIES[CM_ITENS], 0, cache, cdname);
        if (objects == null)
            return document;
        
        docid = document.getId();
        for (ExtendedObject object : objects) {
            name = object.getValue("MODEL");
            model = Model.get(name, cache);
            
            cditens = Query.select(QUERIES[CD_ITENS], 0, cache, docid);
            if (cditens == null)
                continue;
            
            for (ExtendedObject item : cditens)
                document.add(new ExtendedObject(model));
        }
        
        return document;
    }
    
    private static final ComplexDocument getHeader(String name, long id,
            Cache cache) throws Exception {
        long key;
        ComplexDocument document;
        DocumentModel modellink;
        ComplexModel cmmodel = CModel.get(name, cache);
        long cdocid = (cmmodel.getId() * HMULTIPLIER) + id;
        DocumentModel model = Model.get("COMPLEX_DOCUMENT", cache);
        ExtendedObject object = Query.get(model, cdocid, cache.function);
        
        if (object == null)
            return null;
        
        key = object.getValue("ID");
        document = new ComplexDocument(cmmodel);
        document.setId(key);
        modellink = cmmodel.getCDModelLink();
        object = Query.get(modellink, key, cache.function);
        key = 0;
        for (DocumentModelItem item : modellink.getItens()) {
            if (modellink.isKey(item))
                continue;
            key = object.getValue(item);
            break;
        }
        
        object = Query.get(cmmodel.getHeader(), key, cache.function);
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
        long current;
        ExtendedObject[] objects = new ExtendedObject[3];
        
        saveCDocument(document, objects, cache);
        current = saveModelLink(objects, cache);
        saveDocument(objects, document, cache);
        
        return current;
    }
    
    private static final void saveCDocument(ComplexDocument document,
            ExtendedObject[] objects, Cache cache) throws Exception {
        int cmodelid;
        long id, current;
        ExtendedObject[] itens;
        ExtendedObject docitem;
        ComplexModel cmodel = document.getModel();
        String cmodelname = cmodel.getName();
        DocumentModel model = Model.get("COMPLEX_MODEL", cache);
        
        objects[COMPLEX_MODEL] = Query.get(model, cmodelname, cache.function);
        cmodelid = objects[COMPLEX_MODEL].getValue("ID");
        current = objects[COMPLEX_MODEL].getValue("CURRENT");
        
        if (current == 0)
            current = cmodelid * HMULTIPLIER;
        
        current++;
        objects[0].setValue("CURRENT", current);
        if (Query.modify(objects[COMPLEX_MODEL], cache.function) == 0)
            throw new IocasteException("error on complex model update");

        model = Model.get("COMPLEX_DOCUMENT", cache);
        objects[COMPLEX_DOCUMENT] = new ExtendedObject(model);
        objects[COMPLEX_DOCUMENT].setValue("ID", current);
        objects[COMPLEX_DOCUMENT].setValue("COMPLEX_MODEL", cmodel.getName());
        if (Query.save(objects[COMPLEX_DOCUMENT], cache.function) == 0)
            throw new IocasteException("error on insert complex document");
        
        model = Model.get("COMPLEX_DOCUMENT_ITEM", cache);
        id = current * IMULTIPLIER;
        for (String modelname : document.getItensModels()) {
            itens = document.getItens(modelname);
            for (ExtendedObject item : itens) {
                docitem = new ExtendedObject(model);
                id++;
                docitem.setValue("ID", id);
                docitem.setValue("COMPLEX_DOCUMENT", current);
                Query.save(docitem, cache.function);
            }
        }
    }
    
    private static final void saveDocument(ExtendedObject[] objects,
            ComplexDocument document, Cache cache) {
        String name;
        ExtendedObject header = document.getHeader();
        DocumentModel model = objects[LINK].getModel();
        
        for (DocumentModelItem item : model.getItens()) {
            if (model.isKey(item))
                continue;
            name = item.getName();
            header.setValue(name.substring(1), objects[LINK].getValue(name));
        }
        
        Query.save(header, cache.function);
    }
    
    private static final long saveModelLink(ExtendedObject[] objects,
            Cache cache) throws Exception {
        long current = objects[COMPLEX_MODEL].getValue("CURRENT");
        int cmodelid = objects[COMPLEX_MODEL].getValue("ID");
        String modellinkname = objects[COMPLEX_MODEL].getValue("CD_LINK");
        DocumentModel modellink = Model.get(modellinkname, cache);
        
        objects[LINK] = new ExtendedObject(modellink);
        for (DocumentModelItem item : modellink.getItens()) {
            if (modellink.isKey(item)) {
                objects[LINK].setValue(item, current);
                continue;
            }
            current -= (cmodelid * HMULTIPLIER);
            objects[LINK].setValue(item, current);
            break;
        }
        
        if (Query.save(objects[LINK], cache.function) == 0)
            throw new IocasteException("error on insert complex document link");
        
        return current;
    }
}
