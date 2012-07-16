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
    private Map<String, ComplexDocumentItem> itens;
    
    public ComplexDocument(ComplexModel cmodel) {
        this.cmodel = cmodel;
        header = new ExtendedObject(cmodel.getHeader());
        itens = new HashMap<String, ComplexDocumentItem>();
    }
    
    public final void add(ExtendedObject object) {
        ComplexDocumentItem item;
        DocumentModel model = object.getModel();
        
        if (!cmodel.isItemInstanceof(model))
            new RuntimeException("object model item is not allowed for " +
            		cmodel.getName());
        
        if (itens.containsKey(model)) {
            item = itens.get(model);
        } else {
            item = new ComplexDocumentItem(model);
            itens.put(model.getName(), item);
        }
        
        item.add(object);
    }
    
    public final ExtendedObject getHeader() {
        return header;
    }
    
    public final DocumentModel getItemModel(String name) {
        return itens.get(name).getModel();
    }
    
    public final void setHeader(ExtendedObject header) {
        this.header = header;
    }
}

class ComplexDocumentItem implements Serializable {
    private static final long serialVersionUID = 1849261963330378344L;
    private DocumentModel model;
    private Set<ExtendedObject> itens;
    
    public ComplexDocumentItem(DocumentModel model) {
        this.model = model;
        itens = new TreeSet<ExtendedObject>();
    }
    
    public final void add(ExtendedObject object) {
        itens.add(object);
    }
    
    public final DocumentModel getModel() {
        return model;
    }
}