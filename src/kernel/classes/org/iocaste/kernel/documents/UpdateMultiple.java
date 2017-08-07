package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class UpdateMultiple extends AbstractDocumentsHandler {

    @Override
    public final Object run(Message message) throws Exception {
        Query[] queries = message.get("queries");
        Documents documents = getFunction();
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        return run(connection, documents, queries);
    }
    
    public final int run(Connection connection,
            Documents documents, Query[] queries) throws Exception {
        UpdateDocument update;
        int err, c = -1;
        
        update = documents.get("update_document");
        for (Query query : queries) {
            c++;
            err = update.run(connection, documents, query);
            if (err > 0 || query.mustSkipError())
                continue;
            throw new IocasteException(
                    "multiple update error for query %s", c);
        }
        
        return 1;
    }

}
