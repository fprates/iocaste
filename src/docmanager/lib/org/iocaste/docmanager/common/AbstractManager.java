package org.iocaste.docmanager.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Function;

public abstract class AbstractManager implements Manager {
    private DocumentManager docmanager;
    private ComplexModel cmodel;
    private String[] messages;
    private Documents documents;
    
    public AbstractManager(String cmodelname, Function function) {
        docmanager = new DocumentManager(function);
        documents = new Documents(function);
        cmodel = documents.getComplexModel(cmodelname);
        if (cmodel == null)
            throw new RuntimeException(
                    cmodelname.concat(" is an invalid complex model"));
    }
    
    @Override
    public final ComplexDocument clone(ComplexDocument document) {
        Map<String, DocumentModel> models;
        ComplexDocument clone = instance();
        DocumentModel model = cmodel.getHeader();
        ExtendedObject object = new ExtendedObject(model);
        
        /*
         * clone cmodel header object and clear its key field.
         * it's enough to triggers most of reindexing procedures.
         */
        clone.setHeader(object);
        Documents.move(object, document.getHeader());
        for (DocumentModelKey key : model.getKeys()) {
            Documents.clear(object, key.getModelItemName());
            break;
        }
        
        /*
         * clone cmodel items
         */
        models = cmodel.getItems();
        for (String name : models.keySet())
            for (ExtendedObject source : document.getItems(name)) {
                object = new ExtendedObject(models.get(name));
                Documents.move(object, source);
                clone.add(object);
            }
        
        return clone;
    }
    
    /*
     * (não-Javadoc)
     * @see com.b2b.Manager#exists(java.lang.Object)
     */
    @Override
    public final boolean exists(Object code) {
        return docmanager.exists(cmodel.getName(), code);
    }

    /*
     * (não-Javadoc)
     * @see com.b2b.Manager#get(java.lang.Object)
     */
    @Override
    public final ComplexDocument get(Object code) {
        return docmanager.get(cmodel.getName(), code);
    }
    
    /**
     * 
     * @param model
     * @return
     */
    public static final DocumentModelItem getKey(DocumentModel model) {
        String name;
        
        for (DocumentModelKey key : model.getKeys()) {
            name = key.getModelItemName();
            return model.getModelItem(name);
        }
        
        return null;
    }
    
    /*
     * (não-Javadoc)
     * @see com.b2b.Manager#getMessage(int)
     */
    @Override
    public final String getMessage(int messageid) {
        return (messages == null)? null : messages[messageid];
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.docmanager.common.Manager#getModel()
     */
    @Override
    public final ComplexModel getModel() {
        return cmodel;
    }
    
    /**
     * 
     * @param model
     * @param key
     * @return
     */
    public static final DocumentModelItem getReference(DocumentModel model,
            DocumentModelItem key) {
        DocumentModelItem reference;
        
        for (DocumentModelItem item : model.getItens()) {
            reference = item.getReference();
            if (reference == null || !key.equals(reference))
                continue;
            return item;
        }
        return null;
    }

    /*
     * (não-Javadoc)
     * @see org.iocaste.docmanager.common.Manager#getRelated(
     *    org.iocaste.documents.common.ComplexDocument, java.lang.String,
     *    java.lang.String)
     */
    @Override
    public final Map<Object, ExtendedObject> getRelated(
            ComplexDocument document, String itemsname, String field) {
        DocumentModelItem item;
        Query query;
        ExtendedObject[] objects;
        DocumentModelItem reference;
        DocumentModel model;
        
        model = cmodel.getItems().get(itemsname);
        if (model == null)
            throw new RuntimeException(new StringBuilder("items '").
                    append(itemsname).
                    append("' undefined for cmodel ").
                    append(cmodel.getName()).toString());
        
        item = model.getModelItem(field);
        reference = item.getReference();
        if (reference == null)
            return null;
        
        objects = document.getItems(itemsname);
        if (objects == null || objects.length == 0)
            return null;
        
        query = new Query();
        query.setModel(reference.getDocumentModel().getName());
        query.forEntries(objects);
        query.andEqualEntries(reference.getName(), field);
        return documents.selectToMap(query);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.docmanager.common.Manager#instance()
     */
    @Override
    public final ComplexDocument instance() {
        return new ComplexDocument(cmodel);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.docmanager.common.Manager#save(
     *    org.iocaste.documents.common.ExtendedObject, java.util.Collection)
     */
    @Override
    public ComplexDocument save(
            ExtendedObject head, Collection<ExtendedObject[]> groups) {
        return docmanager.save(cmodel.getName(), head, groups);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.docmanager.common.Manager#save(
     *    org.iocaste.documents.common.ComplexDocument)
     */
    @Override
    public ComplexDocument save(ComplexDocument document) {
        Collection<ExtendedObject[]> groups = new ArrayList<>();
        
        for (String name : cmodel.getItems().keySet())
            groups.add(document.getItems(name));
        
        return save(document.getHeader(), groups);
    }
    
    /**
     * 
     * @param messages
     */
    protected final void setMessages(String[] messages) {
        this.messages = messages;
    }

}
