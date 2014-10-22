package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.Query;
import org.iocaste.kernel.documents.AbstractDocumentsHandler;
import org.iocaste.kernel.documents.Documents;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class UpdateDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        Query query = message.get("query");
        return run(connection, documents, query);
    }

    public int run(Connection connection, Documents documents, Query query)
            throws Exception {
        List<Object> values;
        
        if (query.getStatement() == null)
            throw new IocasteException("query statement not defined.");
        
        values = new ArrayList<>();
        String sql = Parser.parseQuery(connection, query, values, documents);
        return update(connection, sql, values.toArray());
    }
}
