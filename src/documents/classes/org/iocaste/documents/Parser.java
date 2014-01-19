package org.iocaste.documents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.JoinClause;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.WhereClause;
import org.iocaste.protocol.IocasteException;

public class Parser {
    
    private static final String columns(Query query, Cache cache)
            throws Exception {
        String[] columns, joinmember;
        DocumentModel model, tablemodel;
        StringBuilder sb = new StringBuilder();
        boolean afterfirst = false;
        
        tablemodel = Model.get(query.getModel(), cache);
        columns = query.getColumns();
        if (columns != null)
            for (String column : columns) {
                if (afterfirst) {
                    sb.append(", ");
                } else {
                    sb.append(" ");
                    afterfirst = true;
                }
                
                joinmember = column.split("\\.");
                if (joinmember.length > 1) {
                    model = Model.get(joinmember[0], cache);
                    sb.append(model.getTableName()).append(".");
                    column = joinmember[1];
                } else {
                    model = tablemodel;
                }
                
                sb.append(model.getModelItem(column).getTableFieldName());
            }
        
        sb.append(" from ").append(tablemodel.getTableName());
        for (String name : query.getJoins()) {
            sb.append(" inner join ");
            sb.append(Model.get(name, cache).getTableName());
            sb.append(" on ");
            afterfirst = false;
            for (JoinClause clause : query.getJoinClauses(name)) {
                if (afterfirst)
                    sb.append(" and ");
                else
                    afterfirst = true;
                
                columns = clause.getOperator(0).split("\\.");
                model = Model.get(columns[0], cache);
                sb.append(model.getTableName());
                sb.append(".");
                sb.append(model.getModelItem(columns[1]).
                        getTableFieldName());
                sb.append("=");
                model = Model.get(name, cache);
                sb.append(model.getTableName());
                sb.append(".");
                sb.append(model.getModelItem(clause.getOperator(1)).
                        getTableFieldName());
            }
        }
        
        return sb.toString();
    }
    
    private static final String orderby(String[] fields, DocumentModel model) {
        boolean started = false;
        StringBuilder sb = new StringBuilder(" order by ");
        
        for (String field : fields) {
            if (!started)
                started = true;
            else
                sb.append(", ");
            
            sb.append(model.getModelItem(field).getTableFieldName());
        }
        
        
        return sb.toString();
    }
    
    /**
     * 
     * @param query
     * @param cache
     * @return
     * @throws Exception
     */
    public static final String parseQuery(Query query, Cache cache,
            List<Object> values) throws Exception {
        String[] fields;
        List<WhereClause> clauses;
        String statement;
        StringBuilder sb;
        DocumentModel tablemodel;
        String modelname = query.getModel();
        
        if (modelname == null)
            throw new IocasteException("model unspecified.");
        
        tablemodel = Model.get(modelname, cache);
        if (tablemodel == null)
            throw new IocasteException(new StringBuilder(modelname).
                    append(" is an invalid model.").toString());
        
        statement = query.getStatement();
        if (statement == null) {
            sb = new StringBuilder("select");
            if (query.getColumns() == null)
                sb.append(" *");
            sb.append(columns(query, cache));
        } else {
            sb = new StringBuilder(statement);
            switch (statement) {
            case "delete":
                sb.append(columns(query, cache));
                break;
            case "update":
                sb.append(" ").append(tablemodel.getTableName());
                sb.append(set(query, tablemodel));
            }
        }
        
        clauses = query.getWhere();
        if (clauses.size() > 0)
            sb.append(where(clauses, tablemodel, values, cache));

        fields = query.getOrderBy();
        if (fields != null) {
            sb.append(orderby(fields, tablemodel));
            if (query.isDescending())
                sb.append(" desc");
        }
        
        return sb.toString();
    }
    
    private static final String set(Query query, DocumentModel model) {
        DocumentModelItem item;
        Object value;
        Map<String, Object> values = query.getValues();
        StringBuilder sb = new StringBuilder(" set ");
        
        for (String name : values.keySet()) {
            if (sb.length() > 5)
                sb.append(", ");
            
            item = model.getModelItem(name);
            sb.append(item.getTableFieldName()).
                append("=");
            
            value = values.get(name);
            switch(item.getDataElement().getType()) {
            case DataType.CHAR:
            case DataType.BOOLEAN:
                sb.append("'").append(value).append("'");
                break;
            default:
                sb.append(value);
                break;
            }
        }
        
        return sb.toString();
    }
    
    private static final String where(List<WhereClause> clauses,
            DocumentModel tablemodel, List<Object> values, Cache cache)
                    throws IocasteException {
        Object value;
        String field;
        DocumentModel model;
        DocumentModelItem item;
        String[] composed;
        StringBuilder sb = null;
        boolean afterfirst = false;
        String operator = null;
        int enclose = 0, level = 0;
        
        for (WhereClause clause : clauses) {
            field = clause.getField();
            if (!afterfirst) {
                afterfirst = true;
                sb = new StringBuilder(" where");
            } else {
                if (field != null && operator != null)
                    sb.append(" ").append(operator);
            }
            
            if (enclose > level) {
                sb.append(" (");
                level = enclose;
            }
            
            if (field != null) {
                composed = field.split("\\.", 2);
                if (composed.length > 1) {
                    model = cache.models.get(composed[0]);
                    if (model == null)
                        throw new IocasteException(new StringBuilder(composed[0]).
                                append(" is an invalid model.").toString());
                    
                    item = model.getModelItem(composed[1]);
                    if (item == null)
                        throw new IocasteException(new StringBuilder(composed[1]).
                                append(" is an invalid item for ").
                                append(composed[0]).toString());
                    
                    operator = new StringBuilder(model.getTableName()).
                            append(".").
                            append(item.getTableFieldName()).toString();
                } else {
                    item = tablemodel.getModelItem(composed[0]);
                    if (item == null)
                        throw new IocasteException(new StringBuilder(composed[0]).
                                append(" is an invalid item for ").
                                append(tablemodel.getTableName()).toString());
                    operator = item.getTableFieldName();
                }
            } else {
                switch (clause.getCondition()) {
                case WhereClause.BE:
                    enclose++;
                    continue;
                case WhereClause.EE:
                    enclose--;
                    break;
                }
            }
            
            if (enclose < level) {
                sb.append(" )");
                level = enclose;
                continue;
            }
            
            value = clause.getValue();
            sb.append(" ").append(operator);
            switch (clause.getCondition()) {
            case WhereClause.EQ:
                sb.append((value == null)? " is NULL" : " = ?");
                break;
            case WhereClause.NE:
                sb.append(" <> ?");
                break;
            case WhereClause.LT:
                sb.append(" < ?");
                break;
            case WhereClause.LE:
                sb.append(" <= ?");
                break;
            case WhereClause.GT:
                sb.append(" > ?");
                break;
            case WhereClause.GE:
                sb.append(" >= ?");
                break;
            case WhereClause.IN:
                sb.append(" in ");
                break;
            }
            
            if (value != null)
                values.add(value);
            operator = clause.getOperator();
        }
        
        return (sb == null)? null : sb.toString();
    }
}

class Components {
    public String arg1, op, arg2, temp, range, logic;
    public List<Object> criteria;
    public boolean ignore;
    
    public Components() {
        temp = "";
        criteria = new ArrayList<Object>();
    }
}
