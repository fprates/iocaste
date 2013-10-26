package org.iocaste.documents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;

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
        Object[] lines;
        Map<String, Object> line;
        ExtendedObject[] objects;
        List<Object> values;
        String statement;
        
        if (query.getStatement() != null)
            throw new Exception("statement for select must be null.");

        values = new ArrayList<>();
        statement = Parser.parseQuery(query, cache, values);
        if (values.size() == 0)
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
