package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.protocol.Message;

public class GetObject extends AbstractDocumentsHandler {
    
    /**
     * 
     * @param model
     * @return
     */
    private final DocumentModelItem getModelKey(DocumentModel model) {
        for (DocumentModelKey key : model.getKeys())
            return model.getModelItem(key.getModelItemName());
        
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object run(Message message) throws Exception {
        String name = message.getString("modelname");
        Object key = message.get("key");
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        GetDocumentModel getmodel = documents.get("get_document_model");
        DocumentModel model = getmodel.run(connection, documents, name);
        String query = new StringBuilder("select * from ").
                append(model.getTableName()).
                append(" where ").
                append(getModelKey(model).getTableFieldName()).
                append(" = ?").toString();
        Object[] objects = select(connection, query, 1, key);
        
        if (objects == null)
            return null;
        
        return SelectDocument.getExtendedObjectFrom(
                model, (Map<String, Object>)objects[0]);
    }

}
