package org.iocaste.documents;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;

public class QueryInfo {
    public DocumentModel model;
    public String query;
    public Object[] criteria;
    public byte command;
    public Set<String> columns;
    public Map<DocumentModel, StringBuilder> join;
    public StringBuilder where;
    
    public QueryInfo() {
        columns = new LinkedHashSet<String>();
        join = new HashMap<DocumentModel, StringBuilder>();
        where = new StringBuilder();
    }
}
