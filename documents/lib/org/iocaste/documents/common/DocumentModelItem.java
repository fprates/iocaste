package org.iocaste.documents.common;

import java.io.Serializable;

public class DocumentModelItem implements Comparable<DocumentModelItem>,
            Serializable {
    private static final long serialVersionUID = 7353680713818082301L;
    private String name;
    private DocumentModel document;
    private DataElement dataelement;
    
    /**
     * 
     * @return
     */
    public DataElement getDataElement() {
        return dataelement;
    }
    
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
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param dataelement
     */
    public void setDataElement(DataElement dataelement) {
        this.dataelement = dataelement;
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
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
        
        if (!name.equals(documentitem.getName()))
            return false;
        
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (name == null)
            return 0;
        
        return (11 * document.hashCode()) + name.hashCode();
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

        return name.compareTo(documentitem.getName());
    }

}
