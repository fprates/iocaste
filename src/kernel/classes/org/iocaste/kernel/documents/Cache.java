package org.iocaste.kernel.documents;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.NameSpace;
import org.iocaste.protocol.Function;

public class Cache {
    public Function function;
    public Map<String, Map<String, String>> queries;
    public Map<String, Map<String, Map<String, String>>> nsqueries;
    private Map<String, DocumentModel> models;
    private Map<String, ComplexModel> cmodels;
    public Map<String, NameSpace[]> nsmodels;
    public DocumentModel mmodel;
    
    public Cache(Function function) {
        this.function = function;
        queries = new HashMap<>();
        models = new HashMap<>();
        cmodels = new HashMap<>();
        nsmodels = new HashMap<>();
        nsqueries = new HashMap<>();
    }
    
    public ComplexModel getCModel(String name) {
        return cmodels.get(name);
    }
    
    public DocumentModel getModel(String name) {
        return models.get(name);
    }
    
    public final void put(String name, DocumentModel model) {
        models.put(name, model);
    }
    
    public final void put(String name, ComplexModel model) {
        cmodels.put(name, model);
    }
    
    public final void removeCModel(String name) {
        cmodels.remove(name);
    }
    
    public final void removeModel(String name) {
        models.remove(name);
    }
}
