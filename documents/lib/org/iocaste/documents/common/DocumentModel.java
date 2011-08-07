package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.Set;

public class DocumentModel implements Comparable<DocumentModel>, Serializable {
    private static final long serialVersionUID = -4964159453586462503L;
    private String name;
    private Set<DocumentModelItem> itens;

    /**
     * 
     * @return
     */
    public Set<DocumentModelItem> getItens() {
        return itens;
    }
    
    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param itens
     */
    public void setItens(Set<DocumentModelItem> itens) {
        this.itens = itens;
    }
    
    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DocumentModel document) {
        if (document == this)
            return 0;
        
        return name.compareTo(document.getName());
    }
    
    
}
