package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetObject extends AbstractDocumentsHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String name = message.getString("modelname");
        Object key = message.get("key");
        Object ns = message.get("ns");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        return run(connection, documents, name, ns, key);
    }
    
    public ExtendedObject run(Connection connection, Documents documents,
            String modelname, Object key) throws Exception {
        return run(connection, documents, modelname, null, key);
    }

    @SuppressWarnings("unchecked")
    public ExtendedObject run(Connection connection, Documents documents,
            String modelname, Object ns, Object key) throws Exception {
        DocumentModelItem item;
        StringBuilder sb;
        Object[] objects;
        String query;
        GetDocumentModel getmodel = documents.get("get_document_model");
        DocumentModel model = getmodel.run(connection, documents, modelname);
        
        if (model == null)
            throw new IocasteException(
                    modelname.concat(" is an invalid model."));
        
        sb = new StringBuilder("select * from ").
                append(model.getTableName()).
                append(" where ").
                append(getModelKey(model).getTableFieldName()).
                append(" = ?");
        
        item = model.getNamespace();
        if (item != null) {
            sb.append(" and ").append(item.getTableFieldName()).append(" = ?");
            query = sb.toString();
            objects = select(connection, query, 1, key, ns);
        } else {
            query = sb.toString();
            objects = select(connection, query, 1, key);
        }
        
        return (objects == null)? null : getExtendedObjectFrom(
                model, (Map<String, Object>)objects[0]);
    }

}
