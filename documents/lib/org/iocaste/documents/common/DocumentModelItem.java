package org.iocaste.documents.common;

import java.io.Serializable;

public class DocumentModelItem implements Comparable<DocumentModelItem>,
            Serializable {
    private static final long serialVersionUID = 7353680713818082301L;
    private String item;
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
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        DocumentModelItem documentitem;
        
        if (object == this)
            return true;
        
        if (!(object instanceof DocumentModelItem))
            return false;
        
        documentitem = (DocumentModelItem)object;
        if (!documentitem.getDocumentModel().equals(document))
            return false;
        
        if (!item.equals(documentitem.getItem()))
            return false;
        
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (item == null)
            return 0;
        
        return (11 * document.hashCode()) + item.hashCode();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DocumentModelItem documentitem) {
        int compare;
        
        if (documentitem.equals(this))
            return 0;
        
        compare = document.compareTo(documentitem.getDocumentModel());
        
        if (compare != 0)
            return compare;

        return item.compareTo(documentitem.getItem());
    }

}
