package org.iocaste.documents;

import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
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
    public static final ComplexDocument get(
            String cdname, Object id, Cache cache) throws Exception {
        DocumentModel model;
        ComplexDocument document;
        ExtendedObject object;
        ExtendedObject[] objects;
        Map<String, DocumentModel> models;
        Query query;
        DocumentModelItem reference, headerkey = null;
        ComplexModel cmodel = CModel.get(cdname, cache);
        
        object = Select.get(cmodel.getHeader(), id, cache.function);
        if (object == null)
            return null;
        
        model = object.getModel();
        headerkey = getModelKey(model);
        if (headerkey == null)
            throw new IocasteException("no valid key found.");
        
        document = new ComplexDocument(cmodel);
        document.setHeader(object);
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            model = models.get(name);
            query = new Query();
            query.setModel(model.getName());
            reference = getReferenceItem(model, headerkey);
            query.andEqual(reference.getName(), id);
            objects = Select.init(query, cache);
            if (objects == null)
                continue;
            document.add(objects);
        }
        
        return document;
    }
    
    private static final DocumentModelItem getModelKey(DocumentModel model) {
        for (DocumentModelKey key : model.getKeys())
            return model.getModelItem(key.getModelItemName());
        return null;
    }
    
    private static final DocumentModelItem getReferenceItem(
            DocumentModel model, DocumentModelItem key) {
        DocumentModelItem reference;
        
        for (DocumentModelItem item : model.getItens()) {
            reference = item.getReference();
            if ((reference == null) || (!reference.equals(key)))
                continue;
            
            return item;
        }
        
        return null;
    }
    
    /**
     * 
     * @param document
     * @param cache
     * @return
     * @throws Exception
     */
    public static final void save(ComplexDocument document, Cache cache)
            throws Exception {
        ComplexModel cmodel;
        Map<String, DocumentModel> models;
        ExtendedObject[] objects;
        ExtendedObject object = document.getHeader();
        
        Save.init(object, cache.function);
        cmodel = document.getModel();
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            objects = document.getItems(name);
            for (ExtendedObject item : objects)
                Save.init(item, cache.function);
        }
    }
    
    public static final void delete(String name, Object id, Cache cache)
            throws Exception {
        DocumentModelItem headerkey, reference;
        DocumentModel model, header;
        Query query;
        Map<String, DocumentModel> models;
        ComplexModel cmodel = CModel.get(name, cache);
        
        header = cmodel.getHeader();
        headerkey = getModelKey(header);
        models = cmodel.getItems();
        for (String item : models.keySet()) {
            model = models.get(item);
            query = new Query("delete");
            query.setModel(model.getName());
            reference = getReferenceItem(model, headerkey);
            query.andEqual(reference.getName(), id);
            Update.init(query, cache);
        }
        
        query = new Query("delete");
        query.setModel(header.getName());
        query.andEqual(headerkey.getName(), id);
        Update.init(query, cache);
    }
}
