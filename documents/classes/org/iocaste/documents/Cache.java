package org.iocaste.documents;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.protocol.Function;

public class Cache {
    public Function function;
    public Map<String, Map<String, String>> queries;
    public Map<String, DocumentModel> models;
    public Map<String, ComplexModel> cmodels;
    
    public Cache(Function function) {
        this.function = function;
        queries = new HashMap<String, Map<String, String>>();
        models = new HashMap<String, DocumentModel>();
        cmodels = new HashMap<String, ComplexModel>();
    }
}
