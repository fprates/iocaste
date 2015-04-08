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
    public Map<String, DocumentModel> models;
    public Map<String, ComplexModel> cmodels;
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
}
