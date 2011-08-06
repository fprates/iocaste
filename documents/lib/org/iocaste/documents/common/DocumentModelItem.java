package org.iocaste.documents.common;

public class DocumentModelItem implements Comparable<DocumentModelItem> {
    private String item;
    private String name;
    private DocumentModel document;
    
    /**
     * 
     * @return
     */
    public final DocumentModel getDocumentModel() {
        return document;
    }
    
    /**
     * 
     * @return
     */
    public final String getItem() {
        return item;
    }
    
    /**
     * 
     * @return
     */
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @param document
     */
    public final void setDocumentModel(DocumentModel document) {
        this.document = document;
    }
    
    /**
     * 
     * @param item
     */
    public final void setItem(String item) {
        this.item = item;
    }
    
    /**
     * 
     * @param name
     */
    public final void setName(String name) {
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
        
        compare = document.getName().compareTo(
                documentitem.getDocumentModel().getName());
        
        if (compare != 0)
            return compare;
        
        return item.compareTo(documentitem.getItem());
    }

}
