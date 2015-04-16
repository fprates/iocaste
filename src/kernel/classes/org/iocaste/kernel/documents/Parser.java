package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.JoinClause;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.WhereClause;
import org.iocaste.protocol.IocasteException;

public class Parser {
    
    private static final void addClause(StringBuilder sb, List<Object> values,
            String operator, String condition, Object value) {
        sb.append(" ").append(operator).append(condition);
        if (value != null)
            values.add(value);
    }
    
    private static final void addEntriesClause(StringBuilder sb,
            List<Object> values, String operator, String condition,
            Object value, ExtendedObject[] entries) throws IocasteException {
        String name;
        StringBuilder entryclause;

        if (entries == null || entries.length == 0)
            throw new IocasteException("no entries for selection.");
        
        sb.append(" (");
        entryclause = null;
        for (ExtendedObject entry : entries) {
            if (entryclause == null)
                entryclause = new StringBuilder();
            else
                entryclause.append(" or ");
            
            name = (String)value;
            if (!entry.getModel().contains((String)value))
                throw new IocasteException(name.concat(" is an invalid "
                        + "item for query entries"));
            addClause(
                    entryclause, values, operator, condition, entry.get(name));
        }
        sb.append(entryclause).append(" )");
    }
    
    private static final String columns(Connection connection, Query query,
            Documents documents) throws Exception {
        GetDocumentModel getmodel;
        String[] columns, joinmember;
        DocumentModel model, tablemodel;
        StringBuilder sb = new StringBuilder();
        boolean afterfirst = false;
        
        getmodel = documents.get("get_document_model");
        tablemodel = getmodel.run(connection, documents, query.getModel());
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
                    model = getmodel.run(connection, documents, joinmember[0]);
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
            sb.append(getmodel.run(connection, documents, name).getTableName());
            sb.append(" on ");
            afterfirst = false;
            for (JoinClause clause : query.getJoinClauses(name)) {
                if (afterfirst)
                    sb.append(" and ");
                else
                    afterfirst = true;
                
                columns = clause.getOperator(0).split("\\.");
                model = getmodel.run(connection, documents, columns[0]);
                sb.append(model.getTableName());
                sb.append(".");
                sb.append(model.getModelItem(columns[1]).
                        getTableFieldName());
                sb.append("=");
                model = getmodel.run(connection, documents, name);
                sb.append(model.getTableName());
                sb.append(".");
                sb.append(model.getModelItem(clause.getOperator(1)).
                        getTableFieldName());
            }
        }
        
        return sb.toString();
    }
    
    private static final String getOperator(Connection connection,
            String[] composed, DocumentModel tablemodel, Documents documents)
                    throws Exception {
        GetDocumentModel getmodel;
        DocumentModel model;
        DocumentModelItem item;
        
        if (composed.length > 1) {
            getmodel = documents.get("get_document_model");
            model = getmodel.run(connection, documents, composed[0]);
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
            item = tablemodel.getModelItem(composed[0]);
            if (item == null)
                throw new IocasteException(new StringBuilder(composed[0]).
                        append(" is an invalid item for ").
                        append(tablemodel.getTableName()).toString());
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
        
        if ((query.getWhere().size() > 0) || query.getNS() != null)
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
        String[] composed;
        ExtendedObject[] entries;
        StringBuilder sb = null;
        boolean afterfirst = false;
        String operator = null;
        int enclose = 0, level = 0;
        List<WhereClause> clauses = query.getWhere();
        
        entries = query.getEntries();
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
                operator = getOperator(
                        connection, composed, tablemodel, documents);
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
            switch (clause.getCondition()) {
            case WhereClause.EQ:
                addClause(sb, values, operator,
                        (value == null)? " is NULL" : " = ?", value);
                break;
            case WhereClause.EQ_ENTRY:
                addEntriesClause(sb, values, operator, " = ?", value, entries);
                break;
            case WhereClause.NE:
                addClause(sb, values, operator, " <> ?", value);
                break;
            case WhereClause.NE_ENTRY:
                addEntriesClause(sb, values, operator, " <> ?", value, entries);
                break;
            case WhereClause.LT:
                addClause(sb, values, operator, " < ?", value);
                break;
            case WhereClause.LT_ENTRY:
                addEntriesClause(sb, values, operator, " < ?", value, entries);
                break;
            case WhereClause.LE:
                addClause(sb, values, operator, " <= ?", value);
                break;
            case WhereClause.LE_ENTRY:
                addEntriesClause(sb, values, operator, " <= ?", value, entries);
                break;
            case WhereClause.GT:
                addClause(sb, values, operator, " > ?", value);
                break;
            case WhereClause.GT_ENTRY:
                addEntriesClause(sb, values, operator, " > ?", value, entries);
                break;
            case WhereClause.GE:
                addClause(sb, values, operator, " >= ?", value);
                break;
            case WhereClause.GE_ENTRY:
                addEntriesClause(sb, values, operator, " >= ?", value, entries);
                break;
            case WhereClause.IN:
                addClause(sb, values, operator, " in ", value);
                break;
            case WhereClause.CP:
                addClause(sb, values, operator, " like ?", value);
                break;
            }
            operator = clause.getOperator();
        }

        nsitem = tablemodel.getNamespace();
        if (nsitem != null) {
            if (sb == null)
                sb = new StringBuilder(" where (");
            else
                sb.append(" and (");
            
            field = nsitem.getTableFieldName();
            sb.append(field).append(" = ?)");
            
            value = query.getNS();
            values.add(value);
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
