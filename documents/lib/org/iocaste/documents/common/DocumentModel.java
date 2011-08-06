package org.iocaste.documents.common;

import java.util.Set;

public class DocumentModel {
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
    
    
}
