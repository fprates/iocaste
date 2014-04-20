package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;

public class ComplexModel implements Serializable, Comparable<ComplexModel> {
    private static final long serialVersionUID = -2741081537248227705L;
    private String name;
    private DocumentModel header;
    private Map<String, DocumentModel> items;
    private Map<String, String> reverseitems;
    
    public ComplexModel(String name) {
        items = new HashMap<>();
        reverseitems = new HashMap<>();
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public final int compareTo(ComplexModel cmodel) {
        if (equals(cmodel))
            return 0;
        
        return name.compareTo(cmodel.getName());
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object object) {
        ComplexModel cmodel;
        
        if (object == this)
            return true;
        
        if (!(object instanceof ComplexModel))
            return false;
        
        cmodel = (ComplexModel)object;
        return name.equals(cmodel.getName());
    }
    
    public final DocumentModel getHeader() {
        return header;
    }
    
    public final Map<String, DocumentModel> getItems() {
        return items;
    }
    
    public final String getName() {
        return name;
    }
    
    public final void put(String name, DocumentModel item) {
        items.put(name, item);
        reverseitems.put(item.getName(), name);
    }
    
    public final void setHeader(DocumentModel header) {
        this.header = header;
    }
}
