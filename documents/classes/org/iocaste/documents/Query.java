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
import org.iocaste.documents.common.RangeOption;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;

public class Query {
    private static final String[] COMPARISON_OPERATORS = {
        "=",
        "<",
        ">",
        "<>",
        ">=",
        "<=",
        "IN",
        "LIKE"
    };
    
    private static final String[] LOGIC_OPERATORS = {
        "AND",
        "OR"
    };
    
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
        
        Object[] objects = iocaste.selectUpTo(query, 1, key);
        
        if (objects == null)
            return null;
        
        return getExtendedObjectFrom(model, (Map<String, Object>)objects[0]);
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
                
            case DataType.DEC:
                value = ((BigDecimal)value).doubleValue();
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
    
    private static final boolean isInvalidOperator(String op,
            String[] operators) {
        String ops = op.trim();
        
        for (String operator : operators)
            if (ops.equals(operator))
                return false;
        
        return true;
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
     * @param cache
     * @return
     * @throws Exception
     */
    public static final QueryInfo parseQuery(String query, Object[] criteria,
            Cache cache) throws Exception {
        String upcasetoken;
        String[] select, parsed = query.split("\\s");
        int t, pass = 0;
        StringBuilder where = new StringBuilder(), sb = new StringBuilder();
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
                queryinfo.model = Model.get(upcasetoken, cache);
                if (queryinfo.model == null)
                    throw new Exception("Document model not found.");
                
                sb.append(queryinfo.model.getTableName());
                pass = 4;
                continue;
            case 4:
                if (!upcasetoken.equals("WHERE"))
                    continue;
                
                pass = 5;
                continue;
            case 5:
                where.append(token).append(" "); 
                continue;
            }
        }
        
        if (where.length() > 0) {
            queryinfo.criteria = criteria;
            upcasetoken = parseWhere(where.toString().trim(), queryinfo);
            if (upcasetoken.length() > 0)
                sb.append(" where ").append(upcasetoken);
        }
        
        queryinfo.query = sb.toString();
        
        return queryinfo;
    }
    
    private static final void parseArg1(char c, Components components) {
        if (Character.isDigit(c) || Character.isAlphabetic(c) || c == '_') {
            components.temp += Character.toUpperCase(c);
            return;
        }
        
        components.arg1 = components.temp;
        components.temp = "";
        
        return;
    }
    
    private static final void parseArg2(char c, Components components) {
        if (c == ' ') {
            components.arg2 = components.temp;
            components.temp = "";
            return;
        }
        
        components.temp += c;
        return;
    }
    
    private static final void parseComparisonOperator(char c,
            Components components) {
        char ch = Character.toUpperCase(c);
        switch (ch) {
        case '=':
        case '>':
        case '<':
        case 'I':
        case 'N':
        case 'L':
        case 'E':
        case 'K':
            components.temp += ch;
            return;
        }
        
        components.op = components.temp;
        components.temp = "";
        return;
    }
    
    private static final boolean parseLogicOperator(char c,
            Components components) {
        if (Character.isAlphabetic(c)) {
            components.temp += Character.toUpperCase(c);
            return false;
        }
        
        components.logic = components.temp;
        return true;
    }
    
    private static final void parseRange(Components components, int argnr,
            Object[] criteria) {
        RangeOption option;
        StringBuilder sb = new StringBuilder();
        ValueRange range = (ValueRange)criteria[argnr];
        
        for (ValueRangeItem item : range.getItens()) {
            if (sb.length() > 1)
                sb.append(" or ");
            
            option = item.getOption();
            components.op = option.getOperator();
            switch (option) {
            case CP:
                components.criteria.add(
                        ((String)item.getLow()).replace("*", "%"));
                break;
            default:
                components.criteria.add((String)item.getLow());
                break;
            }
            
            sb.append(components.arg1).append(" ").
                    append(components.op).append(" ").
                    append(components.arg2);
        }
        
        if (sb.length() == 0) {
            components.ignore = true;
            components.range = null;
            return;
        }
        
        components.range = new StringBuilder("(").append(sb).append(")").
                toString();
    }
    
    /**
     * 
     * @param where
     * @param queryinfo
     * @return
     * @throws Exception
     */
    private static final String parseWhere(String where, QueryInfo queryinfo)
            throws Exception {
        StringBuilder sb;
        List<Object> criteria;
        int argnr = 0;
        Components components = null;
        String lastlogic = null;
        List<Components> args = new ArrayList<Components>();
        
        for (char c : where.toCharArray()) {
            if (components == null)
                components = new Components();
            
            // (arg1) op arg2
            if (components.arg1 == null) {
                parseArg1(c, components);
                if (components.arg1 != null)
                    components.arg1 = queryinfo.model.
                            getModelItem(components.arg1).getTableFieldName();
                continue;
            }
            
            // arg1 (op) arg2
            if (components.op == null) {
                parseComparisonOperator(c, components);
                if (components.op != null)
                    if (isInvalidOperator(components.op, COMPARISON_OPERATORS))
                        throw new Exception(components.op.
                                concat(" is an invalid comparison operator."));
                continue;
            }
            
            // arg1 op (arg2)
            if (components.arg2 == null) {
                parseArg2(c, components);
                if (components.arg2 != null) {
                    if (components.op.equals("IN"))
                        parseRange(components, argnr, queryinfo.criteria);
                    else
                        components.criteria.add(
                                queryinfo.criteria[argnr]);
                    argnr++;
                }
                continue;
            }
            
            // operador lógico
            if (!parseLogicOperator(c, components))
                continue;
            
            if (isInvalidOperator(components.temp, LOGIC_OPERATORS))
                throw new Exception(components.op.
                        concat(" is an invalid logic operator."));
            
            args.add(components);
            components = null;
        }

        if (components.arg1 != null)
            if ((!components.op.equals("IN")) ||
                    (components.op.equals("IN") &&
                            !components.temp.equals("?"))) {
                components.criteria.add(queryinfo.criteria[argnr]);
                args.add(components);
            }
        
        criteria = new ArrayList<Object>();
        sb = new StringBuilder();
        for (Components component : args) {
            if (lastlogic != null)
                sb.append(" ").append(lastlogic).append(" ");
            
            if (component.ignore) {
                lastlogic = null;
                continue;
            }
            
            lastlogic = component.logic;
            if (component.range != null) {
                sb.append(component.range);
            } else {
                sb.append(component.arg1).append(component.op);
                if (component.arg2 == null)
                    sb.append(component.temp);
                else
                    sb.append(component.arg2);
            }
            criteria.addAll(component.criteria);
        }
        
        queryinfo.criteria = criteria.toArray();
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
     * @param rows
     * @param cache
     * @param criteria
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final ExtendedObject[] select(String query, int rows,
            Cache cache, Object... criteria) throws Exception {
        Object[] lines;
        Map<String, Object> line;
        ExtendedObject[] objects;
        QueryInfo queryinfo = parseQuery(query, criteria, cache);
        
        if (queryinfo.query == null || queryinfo.model == null)
            return null;
        
        lines = new Iocaste(cache.function).
                selectUpTo(queryinfo.query, rows, queryinfo.criteria);
        if (lines == null)
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
     * @param cache
     * @param criteria
     * @return
     * @throws Exception
     */
    public static final int update(String query, Cache cache,
            Object... criteria) throws Exception {
        QueryInfo queryinfo = parseQuery(query, criteria, cache);
        
        return new Iocaste(cache.function).update(queryinfo.query, criteria);
    }
}

class Components {
    public String arg1, op, arg2, temp, range, logic;
    public List<Object> criteria;
    public boolean ignore;
    
    public Components() {
        ignore = false;
        arg1 = null;
        op = null;
        arg2 = null;
        temp = "";
        criteria = new ArrayList<Object>();
    }
}
