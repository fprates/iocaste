package org.iocaste.documents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.JoinClause;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.WhereClause;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;

public class Select {
    
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
            Object key, Function function) {
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
     * @param queryinfo
     * @param line
     * @return
     */
    private static final ExtendedObject getExtendedObject2From(Query query,
            Map<String, Object> line, Cache cache) throws Exception {
        DocumentModel model;
        DocumentModelItem item, itemref;
        String[] composed;
        int i;
        
        if (query.getJoins().size() == 0) {
            model = Model.get(query.getModel(), cache);
            return getExtendedObjectFrom(model, line);
        }

        model = new DocumentModel(null);
        i = 0;
        for (String column : query.getColumns()) {
            composed = column.split("\\.");
            item = new DocumentModelItem(composed[1]);
            itemref = Model.get(composed[0], cache).getModelItem(composed[1]);
            item.setTableFieldName(itemref.getTableFieldName());
            item.setIndex(i++);
            item.setDataElement(itemref.getDataElement());
            item.setDocumentModel(model);
            model.add(item);
        }
        
        return getExtendedObjectFrom(model, line);
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
        ExtendedObject object = new ExtendedObject(model);
        
        for (DocumentModelItem modelitem : model.getItens()) {
            value = line.get(modelitem.getTableFieldName());
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

    @SuppressWarnings("unchecked")
    public static final ExtendedObject[] init(Query query, Cache cache)
            throws Exception {
        boolean afterfirst;
        List<Object> values;
        Object[] lines;
        Map<String, Object> line;
        ExtendedObject[] objects;
        String[] columns, joinmember;
        String statement, operator;
        StringBuilder sb;
        DocumentModel tablemodel, model;
        String modelname = query.getModel();
        
        if (modelname == null)
            throw new IocasteException("model unspecified.");
        
        tablemodel = Model.get(modelname, cache);
        if (tablemodel == null)
            throw new IocasteException(new StringBuilder(modelname).
                    append(" is an invalid model.").toString());
            
        sb = new StringBuilder();
        columns = query.getColumns();
        if (columns != null) {
            sb.append("select ");
            afterfirst = false;
            for (String column : columns) {
                if (afterfirst)
                    sb.append(", ");
                else
                    afterfirst = true;
                
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
        } else {
            sb.append("select *");
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
        
        afterfirst = false;
        values = null;
        operator = null;
        for (WhereClause clause : query.getWhere()) {
            if (!afterfirst) {
                afterfirst = true;
                sb.append(" where");
                values = new ArrayList<>();
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
        
        statement = sb.toString();
        System.out.println(statement);
        if (values == null)
            lines = new Iocaste(cache.function).
                selectUpTo(statement, query.getMaxResults());
        else
            lines = new Iocaste(cache.function).
                selectUpTo(statement, query.getMaxResults(), values.toArray());
        
        if (lines == null)
            return null;
        
        objects = new ExtendedObject[lines.length];
        for (int i = 0; i < lines.length; i++) {
            line = (Map<String, Object>)lines[i];
            objects[i] = getExtendedObject2From(query, line, cache);
        }
        
        return objects;
        
    }
}
