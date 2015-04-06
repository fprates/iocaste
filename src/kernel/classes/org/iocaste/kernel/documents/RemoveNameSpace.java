package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class RemoveNameSpace extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        UpdateDocument update;
        Query query;
        Connection connection;
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        String nsname = message.getString("name");
        
        update = documents.get("update_document");
        connection = documents.database.getDBConnection(sessionid);
        
        query = new Query("delete");
        query.setModel("NS_MODELS");
        query.andEqual("NAMESPACE", nsname);
        update.run(connection, documents, query);
        
        query = new Query("delete");
        query.setModel("NS_HEAD");
        query.andEqual("NAME", nsname);
        update.run(connection, documents, query);
        
        return null;
    }

}
