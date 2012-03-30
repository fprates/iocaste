package org.iocaste.packagetool.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;

public class InstallData implements Serializable {
    private static final long serialVersionUID = -4509980464670421174L;
    private List<DocumentModel> models;
    private Map<DocumentModel, List<Object[]>> values;
    private Map<String, String> links;
    private Set<String> numbers;
    private List<SearchHelpData> shds;
    
    public InstallData() {
        models = new ArrayList<DocumentModel>();
        values = new HashMap<DocumentModel, List<Object[]>>();
        links = new HashMap<String, String>();
        numbers = new HashSet<String>();
        shds = new ArrayList<SearchHelpData>();
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
     * @param shd
     */
    public final void add(SearchHelpData shd) {
        shds.add(shd);
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
        List<Object[]> list;
        
        if (this.values.containsKey(model)) {
            list = this.values.get(model);
        } else {
            list = new ArrayList<Object[]>();
            this.values.put(model, list);
        }
        
        list.add(values);
        this.values.put(model, list);
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
     * @return
     */
    public final SearchHelpData[] getSHData() {
        return shds.toArray(new SearchHelpData[0]);
    }
    
    /**
     * 
     * @param model
     * @return
     */
    public final List<Object[]> getValues(DocumentModel model) {
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
