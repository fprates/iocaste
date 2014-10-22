package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class SelectDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        Query query = message.get("query");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        return run(connection, query);
    }

    @SuppressWarnings("unchecked")
    public ExtendedObject[] run(Connection connection, Query query)
            throws Exception {
        Object[] lines;
        Map<String, Object> line;
        ExtendedObject[] objects;
        
        lines = generic(connection, query);
        if (lines == null)
            return null;
        
        objects = new ExtendedObject[lines.length];
        for (int i = 0; i < lines.length; i++) {
            line = (Map<String, Object>)lines[i];
            objects[i] = getExtendedObject2From(connection, query, line);
        }
        
        return objects;
    }
    
    public final Object[] generic(Connection connection, Query query)
            throws Exception {
        List<Object> values;
        String statement;
        Documents documents;
        
        if (query.getStatement() != null)
            throw new Exception("statement for select must be null.");

        documents = getFunction();
        values = new ArrayList<>();
        statement = Parser.parseQuery(connection, query, values, documents);
        if (values.size() == 0)
            return select(connection, statement, query.getMaxResults());
        
        return select(connection, statement, query.getMaxResults(),
                values.toArray());
    }

}
