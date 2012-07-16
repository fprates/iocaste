package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.documents.common.DocumentModel;

public class ComplexModel implements Serializable, Comparable<ComplexModel> {
    private static final long serialVersionUID = -2741081537248227705L;
    private String name;
    private DocumentModel header;
    private Set<DocumentModel> itens;
    
    public ComplexModel() {
        itens = new TreeSet<DocumentModel>();
    }
    
    public final void add(DocumentModel item) {
        itens.add(item);
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
    
    public final DocumentModel[] getItens() {
        return itens.toArray(new DocumentModel[0]);
    }
    
    public final String getName() {
        return name;
    }
    
    public final boolean isItemInstanceof(DocumentModel model) {
        return itens.contains(model);
    }
    
    public final void setHeader(DocumentModel header) {
        this.header = header;
    }
    
    public final void setName(String name) {
        this.name = name;
    }
}
