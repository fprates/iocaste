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
     */
    public static final int delete(Iocaste iocaste, ExtendedObject object) {
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
     * @param queryinfo
     * @param line
     * @return
     */
    private static final ExtendedObject getExtendedObject2From(
            QueryInfo queryinfo, Map<String, Object> line, Cache cache)
                    throws Exception {
        DocumentModel model;
        DocumentModelItem item, itemref;
        String[] composed;
        int i;
        
        if (queryinfo.join.size() == 0)
            return getExtendedObjectFrom(queryinfo.model, line);

        model = new DocumentModel();
        i = 0;
        for (String column : queryinfo.columns) {
            item = new DocumentModelItem();
            composed = column.split("\\.");
            itemref = Model.get(composed[0], cache).getModelItem(composed[1]);
            item.setName(composed[1]);
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
     * @return
     */
    private static final DocumentModelItem getModelKey(DocumentModel model) {
        for (DocumentModelKey key : model.getKeys())
            return model.getModelItem(key.getModelItemName());
        
        return null;
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
        else
            return 0;
        
        if (nrregs == 0)
            if (iocaste.update(model.getQuery("insert"), iargs.toArray()) == 0)
                    throw new IocasteException("Error on object insert");
        
        return 1;
    }
    
    /**
     * 
     * @param object
     * @param function
     * @return
     */
    public static final int save(ExtendedObject object, Function function) {
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
        QueryInfo queryinfo = Parser.parseQuery(query, criteria, cache);
        
        if (queryinfo.query == null || queryinfo.model == null)
            return null;
        
        lines = new Iocaste(cache.function).
                selectUpTo(queryinfo.query, rows, queryinfo.criteria);
        if (lines == null)
            return null;
        
        objects = new ExtendedObject[lines.length];
        for (int i = 0; i < lines.length; i++) {
            line = (Map<String, Object>)lines[i];
            objects[i] = getExtendedObject2From(queryinfo, line, cache);
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
        QueryInfo queryinfo = Parser.parseQuery(query, criteria, cache);
        
        return new Iocaste(cache.function).update(queryinfo.query, criteria);
    }
}
