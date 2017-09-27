package org.iocaste.documents.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.IocasteException;

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
        Object key;
        DocumentModel model;
        ComplexModelItem citem;
        ComplexDocumentItems cdocitems;
        
        if (object == null)
            return;
        model = object.getModel();
        alias = cmodel.getModelItemName(model.getName());
        if (alias == null)
            throw new RuntimeException("Invalid object model.");
        citem = cmodel.getItems().get(alias);
        key = getKey(citem, alias, object, null);
        cdocitems = items.get(alias);
        if (key == null)
            key = cdocitems.objects.size();
        items.get(alias).objects.put(key, object);
    }
    
    public final void add(ComplexDocument document) {
        String alias;
        Object key;
        ComplexModelItem citem;
        ComplexDocumentItems cdocitems;
        
        if (document == null)
            return;
        alias = cmodel.getModelItemName(document.getModel().getName());
        if (alias == null)
            throw new RuntimeException("Invalid object model.");
        citem = cmodel.getItems().get(alias);
        key = getKey(citem, alias, null, document);
        cdocitems = items.get(alias);
        if (key == null)
            key = cdocitems.documents.size();
        cdocitems.documents.put(key, document);
    }
    
    private Object getKey(ComplexModelItem citem, String alias, ExtendedObject object,
            ComplexDocument document) {
        Object key;
        if (object != null)
            key = (citem.index == null)?
                    getKey(cmodel, object.getModel(), object, alias) :
                        object.get(citem.index);
        else
            key = (citem.index == null)?
                    document.getKey() : document.getHeader().get(citem.index);
        return (key instanceof BigDecimal)?
                ((BigDecimal)key).longValue() : key;
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
    public final void add(Collection<ExtendedObject> objects) {
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
        Map<Object, ComplexDocument> cdocuments = items.get(name).documents;
        ComplexDocument[] documents = new ComplexDocument[cdocuments.size()];
        int i = 0;
        for (Object key : cdocuments.keySet())
            documents[i++] = cdocuments.get(key);
        return documents;
    }
    
    public final Map<Object, ComplexDocument> getDocumentsMap(String name) {
        return items.get(name).documents;
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
    
    private final Object getKey(ComplexModel cmodel,
            DocumentModel model, ExtendedObject object, String alias) {
        ComplexModelItem citem = cmodel.getItems().get(alias);
        if (citem.index != null)
            return object.get(citem.index);
        for (DocumentModelKey modelkey : model.getKeys())
            return object.get(modelkey.getModelItemName());
        return null;
    }
    
    /**
     * Get group of items of a document.
     * @param name group name
     * @return group of items
     */
    public final ExtendedObject[] getItems(String name) {
        Map<Object, ExtendedObject> items = this.items.get(name).objects;
        ExtendedObject[] objects = new ExtendedObject[items.size()];
        int i = 0;
        for (Object key : items.keySet())
            objects[i++] = items.get(key);
        return objects;
    }
    
    public final Map<Object, ExtendedObject> getItemsMap(String name) {
        return items.get(name).objects;
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
    
    public final Query getRelated(String itemsname, String field) {
        DocumentModelItem item;
        Query query;
        ExtendedObject[] objects;
        DocumentModelItem reference;
        ComplexModelItem cmodelitem;
        
        cmodelitem = cmodel.getItems().get(itemsname);
        if (cmodelitem.model == null)
            throw new IocasteException("items '%s' undefined for cmodel %s.",
                    itemsname, cmodel.getName());
        
        item = cmodelitem.model.getModelItem(field);
        reference = item.getReference();
        if (reference == null)
            return null;
        
        objects = getItems(itemsname);
        if (objects == null || objects.length == 0)
            return null;
        
        query = new Query();
        query.setModel(reference.getDocumentModel().getName());
        query.forEntries(objects);
        query.andEqualEntries(reference.getName(), field);
        query.setNS(header.getNS());
        return query;        
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
    public final <T> T instance(String item, Object index) {
        ExtendedObject object;
        ComplexDocument document;
        ComplexModelItem cmodelitem = cmodel.getItems().get(item);
        
        if (cmodelitem.model != null) {
            object = new ExtendedObject(cmodelitem.model);
            if (cmodelitem.index != null) {
                if (index == null)
                    throw new RuntimeException(
                            "indexed item can't have null key");
                object.set(cmodelitem.index, index);
                object.setNS(header.getNS());
            }
            add(object);
            return (T)object;
        }
        
        document = new ComplexDocument(cmodelitem.cmodel);
        if (cmodelitem.index != null) {
            document.set(cmodelitem.index, index);
        } else
            for (DocumentModelKey key : cmodelitem.cmodel.getHeader().getKeys())
            {
                document.set(key.getModelItemName(), index);
                break;
            }
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
    
    public final void remove(ExtendedObject object) {
        String alias;
        DocumentModel model;
        if (object == null)
            return;
        model = object.getModel();
        alias = cmodel.getModelItemName(model.getName());
        if (alias == null)
            throw new RuntimeException("Invalid object model.");
        items.get(alias).objects.remove(getKey(cmodel, model, object, alias));
    }
    
    public final void remove(ComplexDocument document) {
        String alias;
        if (document == null)
            return;
        alias = cmodel.getModelItemName(document.getModel().getName());
        if (alias == null)
            throw new RuntimeException("Invalid object model.");
        items.get(alias).documents.remove(document.getKey());
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
