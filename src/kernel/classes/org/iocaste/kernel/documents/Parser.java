package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.JoinClause;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;
import org.iocaste.documents.common.WhereClause;
import org.iocaste.protocol.IocasteException;

public class Parser {
    
    private static final void addClause(WhereData data, String operator,
            String condition, Object value) {
        data.sb.append(" ").append(operator).append(condition);
        if (value != null)
            data.values.add(value);
    }
    
    private static final String columns(Connection connection, Query query,
            Documents documents) throws Exception {
        String[] columns, joinmember;
        String field;
        DocumentModel model, tablemodel;
        DocumentModelItem item;
        Map<String, DocumentModel> models;
        StringBuilder sb = new StringBuilder();
        boolean afterfirst = false;
        
        models = new HashMap<>();
        tablemodel = getModel(models, connection, documents, query.getModel());
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
                    model = getModel(
                            models, connection, documents, joinmember[0]);
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
            sb.append(getModel(models,
                    connection, documents, name).getTableName());
            sb.append(" on ");
            afterfirst = false;
            for (JoinClause clause : query.getJoinClauses(name)) {
                if (afterfirst)
                    sb.append(" and ");
                else
                    afterfirst = true;
                
                columns = clause.getOperator(0).split("\\.");
                model = getModel(models, connection, documents, columns[0]);
                if (model == null)
                    throw new IocasteException(new StringBuilder(columns[0]).
                            append(" is an invalid table in inner join.").
                            toString());
                
                sb.append(model.getTableName());
                sb.append(".");
                if (clause.isNS())
                    field = model.getNamespace().getTableFieldName();
                else
                    field = model.getModelItem(columns[1]).getTableFieldName();
                sb.append(field).append("=");
                model = getModel(models, connection, documents, name);
                sb.append(model.getTableName());
                sb.append(".");
                
                if (clause.isNS()) {
                    item = model.getNamespace();
                } else {
                    field = clause.getOperator(1);
                    item = model.getModelItem(field);
                }
                
                if (item == null)
                    throw new IocasteException(new StringBuilder(field).
                            append(" is an invalid item for ").
                            append(name).toString());
                field = item.getTableFieldName();
                sb.append(field);
            }
        }
        
        return sb.toString();
    }
    
    private static final DocumentModel getModel(
            Map<String, DocumentModel> models, Connection connection,
            Documents documents, String name) throws Exception {
        DocumentModel model;
        GetDocumentModel getmodel;
        
        getmodel = documents.get("get_document_model");
        model = models.get(name);
        if (model != null)
            return model;
        model = getmodel.run(connection, documents, name);
        models.put(name, model);
        return model;
    }
    
    private static final String getOperator(WhereData wheredata, String field)
            throws Exception {
        GetDocumentModel getmodel;
        DocumentModel model;
        DocumentModelItem item;
        String[] composed;

        composed = field.split("\\.", 2);
        if (composed.length > 1) {
            getmodel = wheredata.documents.get("get_document_model");
            model = getmodel.run(
                    wheredata.connection,
                    wheredata.documents,
                    composed[0]);
            if (model == null)
                throw new IocasteException(new StringBuilder(composed[0]).
                        append(" is an invalid model.").toString());
            
            item = model.getModelItem(composed[1]);
            if (item == null)
                throw new IocasteException(new StringBuilder(composed[1]).
                        append(" is an invalid item for ").
                        append(composed[0]).toString());
            
            return new StringBuilder(model.getTableName()).
                    append(".").
                    append(item.getTableFieldName()).toString();
        } else {
            item = wheredata.tablemodel.getModelItem(composed[0]);
            if (item == null)
                throw new IocasteException(new StringBuilder(composed[0]).
                        append(" is an invalid item for ").
                        append(wheredata.tablemodel.getTableName()).toString());
            return item.getTableFieldName();
        }
    }
    
    private static final String orderby(String[] fields, DocumentModel model)
            throws Exception {
        DocumentModelItem item;
        boolean started = false;
        StringBuilder sb = new StringBuilder(" order by ");
        
        for (String field : fields) {
            if (!started)
                started = true;
            else
                sb.append(", ");
            
            item = model.getModelItem(field);
            if (item == null)
                throw new IocasteException(
                        field.concat(" is an invalid field to order by."));
            
            sb.append(item.getTableFieldName());
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
    public static final String parseQuery(Connection connection, Query query,
            List<Object> values, Documents documents) throws Exception {
        boolean nsok;
        GetDocumentModel getmodel;
        String[] fields;
        String statement;
        StringBuilder sb;
        DocumentModel tablemodel;
        String modelname = query.getModel();
        
        if (modelname == null)
            throw new IocasteException("model unspecified.");
        
        getmodel = documents.get("get_document_model");
        tablemodel = getmodel.run(connection, documents, modelname);
        if (tablemodel == null)
            throw new IocasteException(new StringBuilder(modelname).
                    append(" is an invalid model.").toString());
        
        statement = query.getStatement();
        if (statement == null) {
            sb = new StringBuilder("select");
            if (query.getColumns() == null)
                sb.append(" *");
            sb.append(columns(connection, query, documents));
        } else {
            sb = new StringBuilder(statement);
            switch (statement) {
            case "delete":
                sb.append(columns(connection, query, documents));
                break;
            case "update":
                sb.append(" ").append(tablemodel.getTableName());
                sb.append(set(query, tablemodel));
            }
        }
        
        nsok = (tablemodel.getNamespace() != null);
        if ((query.getWhere().size() > 0) || nsok)
            sb.append(where(connection, query, tablemodel, values, documents));
            
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
    
    private static final String where(Connection connection, Query query,
            DocumentModel tablemodel, List<Object> values, Documents documents)
                    throws Exception {
        DocumentModelItem nsitem;
        Object value;
        String field;
        byte condition;
        WhereData data;
        boolean afterfirst = false, entriesprocessed = false;
        boolean skipcondition = false;
        String operator = null;
        
        data = new WhereData();
        data.entries = query.getEntries();
        data.connection = connection;
        data.tablemodel = tablemodel;
        data.documents = documents;
        data.clauses = query.getWhere();
        data.query = query;
        data.values = values;
        for (WhereClause clause : data.clauses) {
            field = clause.getField();
            if (!afterfirst) {
                afterfirst = true;
                data.sb = new StringBuilder(" where");
            } else {
                operator = clause.getOperator();
                if (field != null) {
                    data.sb.append(" ");
                    if ((operator != null) && !skipcondition)
                        data.sb.append(operator);
                    else
                        skipcondition = false;
                }
            }
            
            if (field != null) {
                operator = getOperator(data, field);
            } else {
                switch (clause.getCondition()) {
                case WhereClause.BE:
                    if (operator != null)
                        data.sb.append(" ").append(operator);
                    data.sb.append(" (");
                    skipcondition = true;
                    continue;
                case WhereClause.EE:
                    data.sb.append(" )");
                    continue;
                }
            }
            
            value = clause.getValue();
            condition = clause.getCondition();
            switch (condition) {
            case WhereClause.EQ_ENTRY:
            case WhereClause.NE_ENTRY:
            case WhereClause.LT_ENTRY:
            case WhereClause.LE_ENTRY:
            case WhereClause.GT_ENTRY:
            case WhereClause.GE_ENTRY:
                if (entriesprocessed) {
                    operator = null;
                    continue;
                }
                if (data.entries == null || data.entries.length == 0)
                        throw new IocasteException("no entries for selection.");
                operator = whereEntries(data);
                entriesprocessed = skipcondition = true;
                continue;
            case WhereClause.RG:
                whereRange(data, operator, (ValueRange)value);
                break;
            default:
                whereSimple(data, condition, operator, value);
                break;
            }
        }
        
        nsitem = tablemodel.getNamespace();
        if (nsitem != null) {
            if (data.sb == null)
                data.sb = new StringBuilder(" where (");
            else
                data.sb.append(" and (");
            
            if (query.getJoins() != null)
                data.sb.append(data.tablemodel.getTableName()).append(".");
            field = nsitem.getTableFieldName();
            data.sb.append(field).append(" = ?)");
            
            value = query.getNS();
            values.add((value == null)? "" : value);
        }

        return (data.sb == null)? null : data.sb.toString();
    }
    
    private static final String whereEntries(WhereData data) throws Exception {
        Object value;
        int qt = 0;
        String condition = null, operator = null;
        
        data.sb.append(" (");
        for (ExtendedObject entry : data.entries) {
            data.sb.append((operator == null)? " (" : " ) or (");
            condition = null;
            qt = 0;
            for (WhereClause clause : data.clauses) {
                if (condition != null)
                    data.sb.append(" ").append(condition);
                operator = getOperator(data, clause.getField());
                value = entry.get((String)clause.getValue());
                switch (clause.getCondition()) {
                case WhereClause.EQ_ENTRY:
                    addClause(data, operator,
                            (value == null)? " is NULL" : " = ?", value);
                    qt++;
                    break;
                case WhereClause.NE_ENTRY:
                    addClause(data, operator, " <> ?", value);
                    qt++;
                    break;
                case WhereClause.LT_ENTRY:
                    addClause(data, operator, " < ?", value);
                    qt++;
                    break;
                case WhereClause.LE_ENTRY:
                    addClause(data, operator, " <= ?", value);
                    qt++;
                    break;
                case WhereClause.GT_ENTRY:
                    addClause(data, operator, " > ?", value);
                    qt++;
                    break;
                case WhereClause.GE_ENTRY:
                    addClause(data, operator, " >= ?", value);
                    qt++;
                    break;
                default:
                    condition = null;
                    continue;
                }
                condition = clause.getOperator();
            }
        }
        data.sb.append(") )");
        return (qt != data.clauses.size())? condition : null;
    }
    
    private static final void whereRange(
            WhereData data, String operator, ValueRange range) {
        byte condition;
        
        for (ValueRangeItem rangeitem : range.getItens()) {
            condition = rangeitem.getOption().getOperator();
            switch (condition) {
            case WhereClause.BT:
                addClause(data, operator, " >= ? and", rangeitem.getLow());
                addClause(data, operator, " <= ?", rangeitem.getHigh());
                break;
            default:
                whereSimple(data, condition, operator, rangeitem.getLow());
                break;
            }
        }
    }
    
    private static final void whereSimple(
            WhereData data, byte condition, String operator, Object value) {
        switch (condition) {
        case WhereClause.EQ:
            addClause(data, operator,
                    (value == null)? " is NULL" : " = ?", value);
            break;
        case WhereClause.NE:
            addClause(data, operator, " <> ?", value);
            break;
        case WhereClause.LT:
            addClause(data, operator, " < ?", value);
            break;
        case WhereClause.LE:
            addClause(data, operator, " <= ?", value);
            break;
        case WhereClause.GT:
            addClause(data, operator, " > ?", value);
            break;
        case WhereClause.GE:
            addClause(data, operator, " >= ?", value);
            break;
        case WhereClause.IN:
            addClause(data, operator, " in ", value);
            break;
        case WhereClause.CP:
            addClause(data, operator, " like ?",
                    value.toString().replaceAll("\\*", "%"));
            break;
        }
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

class WhereData {
    public ExtendedObject[] entries;
    public StringBuilder sb;
    public List<Object> values;
    public List<WhereClause> clauses;
    public Query query;
    public Connection connection;
    public DocumentModel tablemodel;
    public Documents documents;
}