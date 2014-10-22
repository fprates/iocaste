package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class GetObject extends AbstractDocumentsHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String name = message.getString("modelname");
        Object key = message.get("key");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        return run(connection, documents, name, key);
    }

    @SuppressWarnings("unchecked")
    public ExtendedObject run(Connection connection, Documents documents,
            String modelname, Object key) throws Exception {
        GetDocumentModel getmodel = documents.get("get_document_model");
        DocumentModel model = getmodel.run(connection, documents, modelname);
        String query = new StringBuilder("select * from ").
                append(model.getTableName()).
                append(" where ").
                append(getModelKey(model).getTableFieldName()).
                append(" = ?").toString();
        Object[] objects = select(connection, query, 1, key);
        
        return (objects == null)? null : getExtendedObjectFrom(
                model, (Map<String, Object>)objects[0]);
    }

}
