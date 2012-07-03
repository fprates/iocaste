package org.iocaste.documents;

import org.iocaste.documents.common.DocumentModel;

public class QueryInfo {
    public DocumentModel model;
    public String query;
    public Object[] criteria;
    
    public QueryInfo() {
        model = null;
        query = null;
    }
}
