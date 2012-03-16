package org.iocaste.packagetool.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;

public class InstallData implements Serializable {
    private static final long serialVersionUID = -4509980464670421174L;
    private List<DocumentModel> models;
    private Map<DocumentModel, Object[]> values;
    
    public InstallData() {
        models = new ArrayList<DocumentModel>();
        values = new HashMap<DocumentModel, Object[]>();
    }
    
    /**
     * 
     * @param model
     */
    public final void add(DocumentModel model) {
        models.add(model);
    }
    
    /**
     * 
     * @param model
     * @param name
     * @param fieldname
     * @param element
     * @param attribute
     * @param iskey
     */
    public final void addModelItem(DocumentModel model, String name,
            String fieldname, DataElement element, String attribute,
            boolean iskey) {
        DocumentModelItem item;
        DocumentModelKey key;
        
        item = new DocumentModelItem();
        item.setName(name);
        item.setTableFieldName(fieldname);
        item.setDataElement(element);
        item.setAttributeName(attribute);
        item.setIndex(model.getItens().length);
        item.setDocumentModel(model);
        
        model.add(item);
        
        if (!iskey)
            return;
        
        key = new DocumentModelKey();
        key.setModel(model);
        key.setModelItem(name);
        model.addKey(key);
        
        return;
    }
    
    /**
     * 
     * @param model
     * @param values
     */
    public final void addValues(DocumentModel model, Object... values) {        
        this.values.put(model, values);
    }
    
    /**
     * 
     * @param name
     * @param decimals
     * @param length
     * @param type
     * @param upcase
     * @return
     */
    public final DataElement getDataElement(String name, int decimals,
            int length, int type, boolean upcase) {
        DataElement element = new DataElement();
        
        element.setName(name);
        element.setDecimals(decimals);
        element.setLength(length);
        element.setType(type);
        element.setUpcase(upcase);
        
        return element;
    }
    
    public final DocumentModel getModel(String name, String tablename,
            String classname) {
        DocumentModel model = new DocumentModel();
        
        model.setName(name);
        model.setTableName(tablename);
        model.setClassName(classname);
        
        add(model);
        
        return model;
    }
    /**
     * 
     * @return
     */
    public final DocumentModel[] getModels() {
        return models.toArray(new DocumentModel[0]);
    }
    
    /**
     * 
     * @param model
     * @return
     */
    public final Object[] getValues(DocumentModel model) {
        return values.get(model);
    }
}
