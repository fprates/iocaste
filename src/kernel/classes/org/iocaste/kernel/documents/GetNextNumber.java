package org.iocaste.kernel.documents;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map;

import org.iocaste.kernel.database.Select;
import org.iocaste.kernel.database.Update;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetNextNumber extends AbstractDocumentsHandler {

    @SuppressWarnings("unchecked")
    @Override
    public Object run(Message message) throws Exception {
        long current;
        Select select;
        Update update;
        Object[] lines;
        Map<String, Object> columns;
        Documents documents;
        Connection connection;
        String serie;
        Object ns;
        String range = message.getst("range");
        
        if (range == null)
            throw new IocasteException("Numeric range not specified.");

        documents = getFunction();
        select = documents.database.get("select");
        connection = documents.database.getDBConnection(message.getSessionid());
        
        serie = message.getst("serie");
        ns = message.get("ns");
        ns = (ns != null)? ns = ns.toString() : "";
        
        if (serie == null) {
            lines = select.run(connection, QUERIES[RANGE], 1, range, ns);
        } else {
            serie = range.concat(serie);
            lines = select.run(connection, QUERIES[RANGE_SERIE], 1, serie, ns);
        }
        
        if (lines == null)
            throw new IocasteException(new StringBuilder("Range \"").
                    append(range).append("\" not found.").toString());
        
        columns = (Map<String, Object>)lines[0];
        current = ((BigDecimal)columns.get("CRRNT")).longValue() + 1;
        update = documents.database.get("update");
        
        if (serie == null)
            update.run(connection, QUERIES[UPDATE_RANGE], current, range, ns);
        else
            update.run(connection, QUERIES[UPDATE_SERIES], current, serie, ns);
        
        return current;
    }

}
