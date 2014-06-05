package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;

/**
 * <p>Specifies a contract to instantiate a ComplexDocument.</p>
 * 
 * <p>ComplexModel is organized in a header and one or several
 * items groups.</p>
 * 
 * <p>The structure of a ComplexModel is documented below:</p>
 * 
 * <p>ComplexModel<br/>
 * - header (DocumentModel)<br/>
 * - items groups<br/>
 * -- group 1 (DocumentModel 1)<br/>
 * -- group 2 (DocumentModel 2)<br/>
 * -- ...<br/>
 * -- group n (DocumentModel n)<br/></p>
 * 
 * @author francisco.prates
 *
 */
public class ComplexModel implements Serializable, Comparable<ComplexModel> {
    private static final long serialVersionUID = -2741081537248227705L;
    private String name;
    private DocumentModel header;
    private Map<String, DocumentModel> items;
    private Map<String, String> reverseitems;
    
    public ComplexModel(String name) {
        items = new HashMap<>();
        reverseitems = new HashMap<>();
        this.name = name;
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
    
    /**
     * Get the header model
     * @return model
     */
    public final DocumentModel getHeader() {
        return header;
    }
    
    /**
     * Get the models of all groups items
     * @return models
     */
    public final Map<String, DocumentModel> getItems() {
        return items;
    }
    
    /**
     * Get the model for a items group
     * @param name items group
     * @return model
     */
    public final String getModelItemName(String name) {
        return reverseitems.get(name);
    }
    
    /**
     * Get the complex model name
     * @return name
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Define a items group
     * @param name name of the group
     * @param item model of the group
     */
    public final void put(String name, DocumentModel item) {
        items.put(name, item);
        reverseitems.put(item.getName(), name);
    }
    
    /**
     * Set a document model for the header
     * @param model document model
     */
    public final void setHeader(DocumentModel model) {
        header = model;
    }
}
