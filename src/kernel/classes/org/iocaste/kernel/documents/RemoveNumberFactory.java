package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class RemoveNumberFactory extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        UpdateDocument update;
        Documents documents;
        Connection connection;
        Query query;
        String name = message.getString("name");
        
        documents = getFunction();
        update = documents.get("update_document");
        connection = documents.database.getDBConnection(message.getSessionid());
        
        query = new Query("delete");
        query.setModel("NUMBER_SERIES");
        query.andEqual("RANGE", name);
        update.run(connection, documents, query);
        
        query = new Query("delete");
        query.setModel("NUMBER_RANGE");
        query.andEqual("IDENT", name);
        return update.run(connection, documents, query);
    }

}
