package org.iocaste.documents.common;

import java.io.Serializable;

public class DocumentModelItem implements Comparable<DocumentModelItem>,
            Serializable {
    private static final long serialVersionUID = 7353680713818082301L;
    private String item;
    private String name;
    private DocumentModel document;
    
    /**
     * 
     * @return
     */
    public DocumentModel getDocumentModel() {
        return document;
    }
    
    /**
     * 
     * @return
     */
    public String getItem() {
        return item;
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
     * @param document
     */
    public void setDocumentModel(DocumentModel document) {
        this.document = document;
    }
    
    /**
     * 
     * @param item
     */
    public void setItem(String item) {
        this.item = item;
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
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DocumentModelItem documentitem) {
        int compare;
        
        if (documentitem == this)
            return 0;
        
        compare = document.compareTo(documentitem.getDocumentModel());
        
        if (compare != 0)
            return compare;
        
        return item.compareTo(documentitem.getItem());
    }

}
