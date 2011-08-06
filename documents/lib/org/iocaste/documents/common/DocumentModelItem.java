package org.iocaste.documents.common;

public class DocumentModelItem implements Comparable<DocumentModelItem> {
    private int item;
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
    public final int getItem() {
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
    public final void setItem(int item) {
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
        if (documentitem == this)
            return 0;
        
        return item - documentitem.getItem();
    }

}
