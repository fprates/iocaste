package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class RemoveComplexModel extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        UpdateDocument update;
        Query query;
        int error;
        String name = message.getString("name");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        update = documents.get("update_document");
        query = new Query("delete");
        query.setModel("COMPLEX_MODEL_ITEM");
        query.andEqual("CMODEL", name);
        update.run(connection, documents, query);
        
        query = new Query("delete");
        query.setModel("COMPLEX_MODEL");
        query.andEqual("NAME", name);
        error = update.run(connection, documents, query);
        if (error > 0)
            documents.cache.cmodels.remove(name);
        
        return error;
    }

}
