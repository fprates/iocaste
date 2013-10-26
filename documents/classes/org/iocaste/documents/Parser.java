package org.iocaste.documents;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.JoinClause;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.WhereClause;
import org.iocaste.protocol.IocasteException;

public class Parser {
    
    /**
     * 
     * @param query
     * @param cache
     * @return
     * @throws Exception
     */
    public static final String parseQuery(Query query, Cache cache,
            List<Object> values) throws Exception {
        boolean afterfirst;
        String operator, statement;
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
            }
        }
        
        afterfirst = false;
        operator = null;
        for (WhereClause clause : query.getWhere()) {
            if (!afterfirst) {
                afterfirst = true;
                sb.append(" where");
            } else {
                if (operator != null)
                    sb.append(" ").append(operator);
            }
            
            sb.append(" ").append(tablemodel.getModelItem(clause.getField()).
                    getTableFieldName());
            switch (clause.getCondition()) {
            case WhereClause.EQ:
                sb.append(" = ?");
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
            
            values.add(clause.getValue());
            operator = clause.getOperator();
        }
        
        return sb.toString();
    }
    
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
