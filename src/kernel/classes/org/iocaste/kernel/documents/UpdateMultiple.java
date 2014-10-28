package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class UpdateMultiple extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        Documents documents;
        UpdateDocument update;
        Connection connection;
        int err, c = -1;
        Query[] queries = message.get("queries");
        
        documents = getFunction();
        connection = documents.database.getDBConnection(message.getSessionid());
        update = documents.get("update_document");
        for (Query query : queries) {
            c++;
            err = update.run(connection, documents, query);
            if (err > 0 || query.mustSkipError())
                continue;
            
            throw new IocasteException(
                    new StringBuilder("multiple update error for query ").
                    append(c).toString());
        }
        
        return 1;
    }

}
