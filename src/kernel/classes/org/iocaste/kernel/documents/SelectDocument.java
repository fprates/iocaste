package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class SelectDocument extends AbstractDocumentsHandler {
    
    /**
     * 
     * @param queryinfo
     * @param line
     * @return
     */
    private final ExtendedObject getExtendedObject2From(Connection connection,
            Query query, Map<String, Object> line) throws Exception {
        GetDocumentModel getmodel;
        DocumentModel model;
        DocumentModelItem item, itemref;
        String[] composed;
        int i;
        Documents documents;
        
        documents = getFunction();
        getmodel = documents.get("get_document_model");
        if (query.getJoins().size() == 0) {
            model = getmodel.run(connection, documents, query.getModel());
            return getExtendedObjectFrom(model, line);
        }

        model = new DocumentModel(null);
        i = 0;
        for (String column : query.getColumns()) {
            composed = column.split("\\.");
            item = new DocumentModelItem(composed[1]);
            itemref = ((DocumentModel)getmodel.run(
                    connection, documents, composed[0])).
                    getModelItem(composed[1]);
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
    private final ExtendedObject getExtendedObjectFrom(
            DocumentModel model, Map<String, Object> line) {
        Object value;
        ExtendedObject object = new ExtendedObject(model);
        
        for (DocumentModelItem modelitem : model.getItens()) {
            value = line.get(modelitem.getTableFieldName());
            object.set(modelitem, value);
        }
        
        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object run(Message message) throws Exception {
        Object[] lines;
        Map<String, Object> line;
        ExtendedObject[] objects;
        Query query = message.get("query");
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        lines = select(connection, query);
        if (lines == null)
            return null;
        
        objects = new ExtendedObject[lines.length];
        for (int i = 0; i < lines.length; i++) {
            line = (Map<String, Object>)lines[i];
            objects[i] = getExtendedObject2From(connection, query, line);
        }
        
        return objects;
    }
    
    private final Object[] select(Connection connection, Query query)
            throws Exception {
        List<Object> values;
        String statement;
        Documents documents;
        
        if (query.getStatement() != null)
            throw new Exception("statement for select must be null.");

        documents = getFunction();
        values = new ArrayList<>();
        statement = Parser.parseQuery(connection, query, values, documents);
        if (values.size() == 0)
            return select(connection, statement, query.getMaxResults());
        
        return select(connection, statement, query.getMaxResults(),
                values.toArray());
    }

}
