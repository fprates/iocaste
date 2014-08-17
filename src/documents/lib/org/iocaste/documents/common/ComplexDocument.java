package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
public class ComplexDocument implements Serializable {
    private static final long serialVersionUID = -6366080783932302245L;
    private ComplexModel cmodel;
    private ExtendedObject header;
    private Map<String, Set<ExtendedObject>> items;
    
    public ComplexDocument(ComplexModel cmodel) {
        this.cmodel = cmodel;
        header = new ExtendedObject(cmodel.getHeader());
        items = new HashMap<>();
        for (String name: cmodel.getItems().keySet())
            items.put(name, new TreeSet<ExtendedObject>());
    }
    
    /**
     * Adds an item to the document
     * @param object extended object
     */
    public final void add(ExtendedObject object) {
        String alias;
        Set<ExtendedObject> objects;
        
        if (object == null)
            return;
        
        alias = cmodel.getModelItemName(object.getModel().getName());
        if (alias == null)
            throw new RuntimeException("Invalid object model.");
        
        objects = items.get(alias);
        objects.add(object);
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
     * Get the extended object in the document header.
     * @return header's extended object
     */
    public final ExtendedObject getHeader() {
        return header;
    }
    
    /**
     * Get group of items of a document.
     * @param name group name
     * @return group of items
     */
    public final ExtendedObject[] getItems(String name) {
        return items.get(name).toArray(new ExtendedObject[0]);
    }
    
    /**
     * Get the complex model of the document
     * @return complex model
     */
    public final ComplexModel getModel() {
        return cmodel;
    }
    
    /**
     * Remove all items of the document
     */
    public final void remove() {
        for (Set<ExtendedObject> objects : items.values())
            objects.clear();
    }
    
    /**
     * Remove specified items group
     * @param name items group name
     */
    public final void remove(String name) {
        items.get(name).clear();
    }
    
    /**
     * Set the header's extended object
     * @param object extended object
     */
    public final void setHeader(ExtendedObject object) {
        header = object;
    }
}