package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;

public class Query {
    
    /**
     * 
     * @param iocaste
     * @param object
     * @return
     * @throws Exception
     */
    public static final int delete(Iocaste iocaste, ExtendedObject object)
            throws Exception {
        int i = 0;
        DocumentModel model = object.getModel();
        Set<DocumentModelKey> keys = model.getKeys();
        Object[] criteria = new Object[keys.size()];
        
        for (DocumentModelKey key : keys)
            criteria[i++] = object.getValue(model.
                    getModelItem(key.getModelItemName()));
        
        return iocaste.update(model.getQuery("delete"), criteria);
    }
    
    /**
     * 
     * @param model
     * @param line
     * @return
     */
    private static final ExtendedObject getExtendedObjectFrom(
            DocumentModel model, Map<String, Object> line) {
        Object value;
        DataElement element;
        ExtendedObject object = new ExtendedObject(model);
        
        for (DocumentModelItem modelitem : model.getItens()) {
            value = line.get(modelitem.getTableFieldName());
            element = modelitem.getDataElement();
            
            switch (element.getType()) {
            case DataType.NUMC:
                if (element.getLength() < DataType.MAX_INT_LEN)
                    value = (value == null)? 0 : ((BigDecimal)value).intValue();
                else
                    value = (value == null)?
                            0l : ((BigDecimal)value).longValue();
                break;
            }
            
            object.setValue(modelitem, value);
        }
        
        return object;
    }
    
    /**
     * 
     * @param model
     * @return
     */
    private static final DocumentModelItem getModelKey(DocumentModel model) {
        for (DocumentModelKey key : model.getKeys())
            return model.getModelItem(key.getModelItemName());
        
        return null;
    }
    
    /**
     * 
     * @param model
     * @param key
     * @param function
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final ExtendedObject get(DocumentModel model,
            Object key, Function function) throws Exception {
        Iocaste iocaste = new Iocaste(function);
        String query = new StringBuilder("select * from ").
                append(model.getTableName()).
                append(" where ").
                append(getModelKey(model).getTableFieldName()).
                append(" = ?").toString();
        
        Object[] objects = iocaste.select(query, key);
        
        if (objects.length == 0)
            return null;
        
        return getExtendedObjectFrom(model, (Map<String, Object>)objects[0]);
    }
    
    /**
     * 
     * @param object
     * @param function
     * @return
     * @throws Exception
     */
    public static final int modify(ExtendedObject object, Function function)
            throws Exception {
        String query;
        Object value;
        int nrregs;
        DocumentModel model = object.getModel();
        List<Object> criteria = new ArrayList<Object>();
        List<Object> uargs = new ArrayList<Object>();
        List<Object> iargs = new ArrayList<Object>();
        Iocaste iocaste = new Iocaste(function);
        
        for (DocumentModelItem item : model.getItens()) {
            value = object.getValue(item);
            
            iargs.add(value);
            if (model.isKey(item))
                criteria.add(value);
            else
                uargs.add(value);
        }
        
        uargs.addAll(criteria);
        nrregs = 0;
        
        query = model.getQuery("update");
        if (query != null)
            nrregs = iocaste.update(query, uargs.toArray());
        
        if (nrregs == 0)
            if (iocaste.update(model.getQuery("insert"), iargs.toArray()) == 0)
                    throw new IocasteException("");
        
        return 1;
    }
    
    /**
     * 
     * @param query
     * @param function
     * @param queries
     * @return
     * @throws Exception
     */
    public static final QueryInfo parseQuery(String query, Function function,
            Map<String, Map<String, String>> queries) throws Exception {
        String where, criteria = "", upcasetoken;
        String[] select, parsed = query.split("\\s");
        int t, pass = 0;
        StringBuilder sb = new StringBuilder();
        QueryInfo queryinfo = new QueryInfo();
        
        for (String token : parsed) {
            upcasetoken = token.toUpperCase();
            
            switch (pass) {
            case 0:
                if (upcasetoken.equals("SELECT")) {
                    sb.append("select ");
                    pass = 1;
                    continue;
                }
                
                if (upcasetoken.equals("FROM")) {
                    sb.append("select * from ");
                    pass = 3;
                    continue;
                }
                
                if (upcasetoken.equals("DELETE")) {
                    sb.append("delete ");
                    pass = 2;
                    continue;
                }
                
                continue;
            case 1:
                select = token.split(",");
                t = select.length;
                
                for (int i = 0; i < t; i++) {
                    sb.append(select[i]);
                    if (i == (t - 1))
                        continue;
                    sb.append(",");
                }
                
                pass = 2;
                continue;
            case 2:
                if (upcasetoken.equals("FROM"))
                    sb.append(" from ");
                
                pass = 3;
                continue;
            case 3:
                queryinfo.model = Model.get(upcasetoken, function, queries);
                if (queryinfo.model == null)
                    throw new Exception("Document model not found.");
                
                sb.append(queryinfo.model.getTableName());
                pass = 4;
                continue;
            case 4:
                if (upcasetoken.equals("WHERE"))
                    sb.append(" where ");
                else
                    continue;
                
                pass = 5;
                continue;
            case 5:
                criteria += token; 
                continue;
            }
        }
        
        where = parseWhere(criteria, queryinfo.model);
        sb.append(where);
        
        queryinfo.query = sb.toString();
        
        return queryinfo;
    }
    
    /**
     * 
     * @param criteria
     * @param model
     * @return
     */
    private static final String parseWhere(String criteria, DocumentModel model) {
        StringBuilder sb = new StringBuilder();
        char[] charcrit = criteria.toCharArray();
        String temp = "", arg1 = null, arg2 = null, op = null;
        
        for (int i = 0; i < charcrit.length; i++) {
            /*
             * (arg1) op arg2
             */
            if (arg1 == null) {
                charcrit[i] = Character.toUpperCase(charcrit[i]);
                
                if ((charcrit[i] >= 'A' && charcrit[i] <= 'Z') ||
                        charcrit[i] == '_') {
                    temp += charcrit[i];
                    continue;
                } else {
                    arg1 = temp;
                    temp = "";
                    op = null;
                }
            }
            
            /*
             * arg1 (op) arg2
             */
            if (op == null) {
                if ((charcrit[i] >= '1' && charcrit[i] <= '0') ||
                        charcrit[i] == '?') {
                    op = temp;
                    temp = "";
                    arg2 = null;
                } else {
                    temp += charcrit[i];
                    continue;
                }
            }
            
            /*
             * arg1 op (arg2)
             */
            if (arg2 == null) {
                if ((charcrit[i] >= '1' && charcrit[i] <= '0') ||
                        (charcrit[i] >= 'A' && charcrit[i] <= 'Z') ||
                        (charcrit[i] == '?')) {
                    temp += charcrit[i];
                    
                    if (charcrit[i] == '?') {
                        arg2 = temp;
                        
                        sb.append(model.getModelItem(arg1).
                                        getTableFieldName()).append(op).
                                        append(arg2);
                    }
                    
                    continue;
                } else {
                    arg2 = temp;
                    
                    sb.append (" ").
                            append(model.getModelItem(arg1)).
                            append(op).append(arg2);
                }
            }
        }
        
        return sb.toString();
    }
    
    /**
     * 
     * @param object
     * @param function
     * @return
     * @throws Exception
     */
    public static final int save(ExtendedObject object, Function function)
            throws Exception {
        Object[] criteria;
        DocumentModel model = object.getModel();
        DocumentModelItem[] itens = model.getItens();
        int i = itens.length;
        Iocaste iocaste = new Iocaste(function);
        
        criteria = (i > 0)? new Object[i] : null;
        
        i = 0;
        for (DocumentModelItem item : model.getItens())
            criteria[i++] = object.getValue(item);
        
        return iocaste.update(model.getQuery("insert"), criteria);
    }
    
    /**
     * 
     * @param query
     * @param criteria
     * @param function
     * @param queries
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final ExtendedObject[] select(String query, Function function,
            Map<String, Map<String, String>> queries, Object... criteria)
                    throws Exception {
        Iocaste iocaste; 
        Object[] lines;
        Map<String, Object> line;
        ExtendedObject[] objects;
        QueryInfo queryinfo = parseQuery(query, function, queries);
        
        if (queryinfo.query == null || queryinfo.model == null)
            return null;

        iocaste = new Iocaste(function);
        
        lines = iocaste.select(queryinfo.query, criteria);
        if (lines.length == 0)
            return null;
        
        objects = new ExtendedObject[lines.length];
        
        for (int i = 0; i < lines.length; i++) {
            line = (Map<String, Object>)lines[i];
            objects[i] = getExtendedObjectFrom(queryinfo.model, line);
        }
        
        return objects;
    }
    
    /**
     * 
     * @param query
     * @param function
     * @param queries
     * @param criteria
     * @return
     * @throws Exception
     */
    public static final int update(String query, Function function,
            Map<String, Map<String, String>> queries, Object... criteria)
                    throws Exception {
        QueryInfo queryinfo = parseQuery(query, function, queries);
        
        return new Iocaste(function).update(queryinfo.query, criteria);
    }

}
