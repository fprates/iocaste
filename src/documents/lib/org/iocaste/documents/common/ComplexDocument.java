package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
     * 
     * @param object
     */
    public final void add(ExtendedObject object) {
        String model;
        Set<ExtendedObject> objects;
        
        if (object == null)
            return;
        
        model = object.getModel().getName();
        objects = items.get(model);
        if (objects == null)
            throw new RuntimeException("Invalid object model.");
        
        objects.add(object);
    }
    
    public final void add(ExtendedObject[] objects) {
        if (objects == null)
            return;
        
        for (ExtendedObject object : objects)
            add(object);
    }
    
    /**
     * 
     * @return
     */
    public final ExtendedObject getHeader() {
        return header;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ExtendedObject[] getItems(String name) {
        return items.get(name).toArray(new ExtendedObject[0]);
    }
    
    /**
     * 
     * @return
     */
    public final ComplexModel getModel() {
        return cmodel;
    }
    
    /**
     * 
     */
    public final void remove() {
        for (Set<ExtendedObject> objects : items.values())
            objects.clear();
    }
    
    /**
     * 
     * @param header
     */
    public final void setHeader(ExtendedObject header) {
        this.header = header;
    }
}