package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexDocument implements Serializable {
    private static final long serialVersionUID = -6366080783932302245L;
    private ComplexModel cmodel;
    private ExtendedObject header;
    private Map<String, ComplexDocumentItem> itens;
    private long id, last;
    
    public ComplexDocument(ComplexModel cmodel) {
        this.cmodel = cmodel;
        header = new ExtendedObject(cmodel.getHeader());
        itens = new HashMap<String, ComplexDocumentItem>();
        for (DocumentModel model : cmodel.getItens())
            itens.put(model.getName(), new ComplexDocumentItem(model));
    }
    
    /**
     * 
     * @param object
     */
    public final void add(ExtendedObject object) {
        Object value;
        ComplexDocumentItem item;
        long index;
        DocumentModel model = object.getModel();
        
        if (!cmodel.isItemInstanceof(model))
            new RuntimeException("object model item is not allowed for " +
            		cmodel.getName());
        
        for (DocumentModelItem modelitem : model.getItens()) {
            if (!model.isKey(modelitem))
                continue;
            
            value = object.getValue(modelitem);
            index = (value == null)? 0 : (Long)value;
            if (index > 0) {
                if (index > last)
                    last = index;
                continue;
            }
            
            last++;
            object.setValue(modelitem, last);
            break;
        }
        
        item = itens.get(model.getName());
        item.add(object);
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
     * @return
     */
    public final long getId() {
        return id;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final DocumentModel getItemModel(String name) {
        return itens.get(name).getModel();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ExtendedObject[] getItens(String name) {
        return itens.get(name).getItens();
    }
    
    /**
     * 
     * @return
     */
    public final String[] getItensModels() {
        return itens.keySet().toArray(new String[0]);
    }
    
    /**
     * 
     * @return
     */
    public final ComplexModel getModel() {
        return cmodel;
    }
    
    public final void remove() {
        for (String name : itens.keySet())
            itens.get(name).remove();
    }
    
    /**
     * 
     * @param header
     */
    public final void setHeader(ExtendedObject header) {
        this.header = header;
    }
    
    /**
     * 
     * @param id
     */
    public final void setId(long id) {
        this.id = id;
    }
}

class ComplexDocumentItem implements Serializable {
    private static final long serialVersionUID = 1849261963330378344L;
    private DocumentModel model;
    private List<ExtendedObject> itens;
    
    public ComplexDocumentItem(DocumentModel model) {
        this.model = model;
        itens = new ArrayList<ExtendedObject>();
    }
    
    public final void add(ExtendedObject object) {
        itens.add(object);
    }
    
    public final ExtendedObject[] getItens() {
        return itens.toArray(new ExtendedObject[0]);
    }
    
    public final DocumentModel getModel() {
        return model;
    }
    
    public final void remove() {
        itens.clear();
    }
}