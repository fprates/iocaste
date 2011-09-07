package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class DocumentModel implements Comparable<DocumentModel>, Serializable {
    private static final long serialVersionUID = -4964159453586462503L;
    private String name;
    private Set<DocumentModelItem> itens;

    public DocumentModel() {
        itens = new TreeSet<DocumentModelItem>();
    }
    
    /**
     * 
     * @param item
     */
    public final void add(DocumentModelItem item) {
        itens.add(item);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DocumentModel document) {
        if (document.equals(document))
            return 0;
        
        return name.compareTo(document.getName());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        DocumentModel document;
        
        if (object == this)
            return true;
        
        if (!(object instanceof DocumentModel))
            return false;
        
        document = (DocumentModel)object;
        
        return name.equals(document.getName());
    }
    
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
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (name == null)?0 : name.hashCode();
    }
    
    /**
     * 
     * @param itens
     */
    protected void setItens(Set<DocumentModelItem> itens) {
        this.itens = itens;
    }
    
    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
}
