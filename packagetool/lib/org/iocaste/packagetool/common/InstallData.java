package org.iocaste.packagetool.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;

public class InstallData implements Serializable {
    private static final long serialVersionUID = -4509980464670421174L;
    private List<DocumentModel> models;
    private Map<DocumentModel, Object[]> values;
    private Map<String, String> links;
    private Set<String> numbers;
    
    public InstallData() {
        models = new ArrayList<DocumentModel>();
        values = new HashMap<DocumentModel, Object[]>();
        links = new HashMap<String, String>();
        numbers = new HashSet<String>();
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
     * @return
     */
    public final DocumentModelItem addModelItem(DocumentModel model,
            String name, String fieldname, DataElement element,
            String attribute, boolean iskey) {
        
        return addModelItem(model, name, fieldname, element, attribute, iskey,
                null);
    }
    
    /**
     * 
     * @param model
     * @param name
     * @param fieldname
     * @param element
     * @param attribute
     * @param iskey
     * @param reference
     * @return
     */
    public final DocumentModelItem addModelItem(DocumentModel model, String name,
            String fieldname, DataElement element, String attribute,
            boolean iskey, DocumentModelItem reference) {
        DocumentModelItem item;
        DocumentModelKey key;
        
        item = new DocumentModelItem();
        item.setName(name);
        item.setTableFieldName(fieldname);
        item.setDataElement(element);
        item.setAttributeName(attribute);
        item.setIndex(model.getItens().length);
        item.setDocumentModel(model);
        item.setReference(reference);
        
        model.add(item);
        
        if (!iskey)
            return item;
        
        key = new DocumentModelKey();
        key.setModel(model);
        key.setModelItem(name);
        model.addKey(key);
        
        return item;
    }
    
    /**
     * 
     * @param name
     */
    public final void addNumberFactory(String name) {
        numbers.add(name);
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
    
    /**
     * 
     * @return
     */
    public final Map<String, String> getLinks() {
        return links;
    }
    
    /**
     * 
     * @param name
     * @param tablename
     * @param classname
     * @return
     */
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
     * @return
     */
    public final String[] getNumberFactories() {
        return numbers.toArray(new String[0]);
    }
    
    /**
     * 
     * @param model
     * @return
     */
    public final Object[] getValues(DocumentModel model) {
        return values.get(model);
    }
    
    /**
     * 
     * @param link
     * @param command
     */
    public final void link(String link, String command) {
        links.put(link, command);
    }
}
