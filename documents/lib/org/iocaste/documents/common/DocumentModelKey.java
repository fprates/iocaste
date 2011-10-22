package org.iocaste.documents.common;

import java.io.Serializable;

public class DocumentModelKey implements Comparable<DocumentModelKey>, Serializable {
    private static final long serialVersionUID = -9169359567383979476L;
    private DocumentModel model;
    private String item;

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DocumentModelKey modelkey) {
        int compare;
        
        if (equals(modelkey))
            return 0;
        
        compare = model.compareTo(modelkey.getModel());
        if (compare != 0)
            return compare;
        
        compare = item.compareTo(modelkey.getModelItem());
        if (compare != 0)
            return compare;
        
        return 0;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        DocumentModelKey modelkey;
        
        if (object == this)
            return true;
        
        if (!(object instanceof DocumentModelKey))
            return false;
        
        modelkey = (DocumentModelKey)object;
        if (!model.equals(modelkey.getModel()))
            return false;
        
        if (!item.equals(modelkey.getModelItem()))
            return false;
        
        return true;
    }
    
    /**
     * Retorna modelo de documento.
     * @return modelo
     */
    public DocumentModel getModel() {
        return model;
    }
    
    /**
     * Retorna nome do item do modelo de documento.
     * @return nome do item
     */
    public String getModelItem() {
        return item;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return item.hashCode();
    }
    
    /**
     * Define modelo de documento.
     * @param model modelo
     */
    public void setModel(DocumentModel model) {
        this.model = model;
    }
    
    /**
     * define item do modelo de documento.
     * @param item item de modelo
     */
    public void setModelItem(String item) {
        this.item = item;
    }
}
