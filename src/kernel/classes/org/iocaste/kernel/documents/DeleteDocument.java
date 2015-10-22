package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class DeleteDocument extends AbstractDocumentsHandler {

    @Override    
    public Object run(Message message) throws Exception {
        Connection connection;
        Documents documents = getFunction();
        ExtendedObject object = message.get("object");
        
        connection = documents.database.getDBConnection(message.getSessionid());
        return run(documents, connection, object);
    }

    public int run(Documents documents, Connection connection,
            ExtendedObject object) throws Exception {
        String query;
        DocumentModel model;
        Set<DocumentModelKey> keys;
        Object[] criteria;
        int i = 0;

        model = object.getModel();
        keys = model.getKeys();
        criteria = new Object[keys.size()];
        for (DocumentModelKey key : keys)
            criteria[i++] = object.get(model.getModelItem(
                    key.getModelItemName()));
        
        query = documents.cache.queries.get(model.getName()).get("delete");
        return update(connection, query, criteria);
    }

}
