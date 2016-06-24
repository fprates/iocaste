package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This class groups ExtendedObjects in one document, following the
 * ComplexModel contract.</p>
 * 
 * <p>The document layout is defined by a header and one or more items.</p>
 * <p>Only registered models in ComplexModel can be stored.</p>
 * 
 * @author francisco.prates
 *
 */
public class ComplexDocument implements Serializable,
        Comparable<ComplexDocument> {
    private static final long serialVersionUID = -6366080783932302245L;
    private ComplexModel cmodel;
    private ExtendedObject header;
    private Map<String, ComplexDocumentItems> items;
    
    public ComplexDocument(ComplexModel cmodel) {
        Map<String, ComplexModelItem> models;
        
        this.cmodel = cmodel;
        header = new ExtendedObject(cmodel.getHeader());
        items = new HashMap<>();
        models = cmodel.getItems();
        for (String name: models.keySet())
            items.put(name, new ComplexDocumentItems(models.get(name)));
    }
    
    /**
     * Adds an item to the document
     * @param object extended object
     */
    public final void add(ExtendedObject object) {
        String alias;
        
        if (object == null)
            return;
        
        alias = cmodel.getModelItemName(object.getModel().getName());
        if (alias == null)
            throw new RuntimeException("Invalid object model.");
        
        items.get(alias).objects.add(object);
    }
    
    public final void add(ComplexDocument document) {
        String alias;
        
        if (document == null)
            return;
        
        alias = cmodel.getModelItemName(document.getModel().getName());
        if (alias == null)
            throw new RuntimeException("Invalid object model.");
        
        items.get(alias).documents.add(document);
    }
    
    /**
     * Adds an array of items to the document
     * @param objects array of extended objects
     */
    public final void add(ExtendedObject[] objects) {
        if (objects == null)
            return;
        
        for (ExtendedObject object : objects)
            add(object);
    }
    
    /**
     * Adds an array of items to the document
     * @param objects array of extended objects
     */
    public final void add(List<ExtendedObject> objects) {
        if (objects == null)
            return;
        
        for (ExtendedObject object : objects)
            add(object);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(ComplexDocument document) {
        String value1, value2;
        
        value1 = getKey().toString();
        value2 = document.getKey().toString();
        return (value1.compareTo(value2));
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ComplexDocumentItems get(String name) {
        return items.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final byte getbKey() {
        for (DocumentModelKey key : cmodel.getHeader().getKeys())
            return header.getb(key.getModelItemName());
        return 0;
    }
    
    /**
     * Get group of complexes documents of a document.
     * @param name group name
     * @return group of complexes documents
     */
    public final ComplexDocument[] getDocuments(String name) {
        return items.get(name).documents.toArray(new ComplexDocument[0]);
    }
    
    public final Map<Object, ComplexDocument> getDocumentsMap(String name) {
        Map<Object, ComplexDocument> documents;
        documents = new LinkedHashMap<>();
        for (ComplexDocument document : items.get(name).documents)
            documents.put(document.getKey(), document);
        return documents;
    }
    
    /**
     * Get the extended object in the document header.
     * @return header's extended object
     */
    public final ExtendedObject getHeader() {
        return header;
    }
    
    /**
     * 
     * @return
     */
    public final int getiKey() {
        for (DocumentModelKey key : cmodel.getHeader().getKeys())
            return header.geti(key.getModelItemName());
        return 0;
    }
    
    /**
     * Get group of items of a document.
     * @param name group name
     * @return group of items
     */
    public final ExtendedObject[] getItems(String name) {
        return items.get(name).objects.toArray(new ExtendedObject[0]);
    }
    
    public final Map<Object, ExtendedObject> getItemsMap(String name) {
        Map<Object, ExtendedObject> items = new LinkedHashMap<>();
        DocumentModel model = cmodel.getItems().get(name).model;
        String key = null;
        
        for (DocumentModelKey modelkey : model.getKeys()) {
            key = modelkey.getModelItemName();
            break;
        }
        
        for (ExtendedObject object : this.items.get(name).objects)
            items.put(object.get(key), object);
        return items;
    }
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T> T getKey() {
        for (DocumentModelKey key : cmodel.getHeader().getKeys())
            return (T)header.get(key.getModelItemName());
        return null;
    }
    
    /**
     * 
     * @return
     */
    public final long getlKey() {
        for (DocumentModelKey key : cmodel.getHeader().getKeys())
            return header.getl(key.getModelItemName());
        return 0;
    }
    
    /**
     * Get the complex model of the document
     * @return complex model
     */
    public final ComplexModel getModel() {
        return cmodel;
    }
    
    /**
     * 
     * @return
     */
    public final Object getNS() {
        return header.getNS();
    }
    
    /**
     * 
     * @return
     */
    public final String getstKey() {
        for (DocumentModelKey key : cmodel.getHeader().getKeys())
            return header.getst(key.getModelItemName());
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public final <T> T instance(String item) {
        ExtendedObject object;
        ComplexDocument document;
        ComplexModelItem cmodelitem = cmodel.getItems().get(item);
        
        if (cmodelitem.model != null) {
            object = new ExtendedObject(cmodelitem.model);
            add(object);
            return (T)object;
        }
        
        document = new ComplexDocument(cmodelitem.cmodel);
        add(document);
        return (T)document;
    }
    
    /**
     * Remove all items of the document
     */
    public final void remove() {
        for (ComplexDocumentItems items : items.values())
            items.clear();
    }
    
    /**
     * Remove specified items group
     * @param name items group name
     */
    public final void remove(String name) {
        items.get(name).clear();
    }
    
    /**
     * 
     * @param field
     * @param value
     */
    public final void set(String field, Object value) {
        header.set(field, value);
    }
    
    /**
     * Set the header's extended object
     * @param object extended object
     */
    public final void setHeader(ExtendedObject object) {
        header = object;
    }
    
    /**
     * 
     * @param ns
     */
    public final void setNS(Object ns) {
        header.setNS(ns);;
    }
}
