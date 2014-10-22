package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class SelectToMap extends AbstractDocumentsHandler {

    @SuppressWarnings("unchecked")
    @Override
    public Object run(Message message) throws Exception {
        GetDocumentModel getmodel;
        SelectDocument select;
        Object[] lines;
        Map<String, Object> line;
        ExtendedObject object;
        Map<Object, ExtendedObject> objects;
        DocumentModel model;
        String key;
        Query query = message.get("query");
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        select = documents.get("select_document");
        lines = select.generic(connection, query);
        if (lines == null)
            return null;

        key = null;
        getmodel = documents.get("get_document_model");
        model = getmodel.run(connection, documents, query.getModel());
        for (DocumentModelKey item : model.getKeys()) {
            key = item.getModelItemName();
            break;
        }
        
        objects = new LinkedHashMap<>();
        for (int i = 0; i < lines.length; i++) {
            line = (Map<String, Object>)lines[i];
            object = getExtendedObject2From(connection, query, line);
            objects.put(object.get(key), object);
        }
        
        return objects;
    }

}
