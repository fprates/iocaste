package org.iocaste.kernel.documents;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map;

import org.iocaste.kernel.database.Select;
import org.iocaste.kernel.database.Update;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetNextNumber extends AbstractDocumentsHandler {

    @Override
    public final Object run(Message message) throws Exception {
        String range = message.getst("range");
        String serie = message.getst("serie");
        Object ns = message.get("ns");
        Documents documents = getFunction();
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        return run(connection, documents, range, serie, ns);
    }

    @SuppressWarnings("unchecked")
    public final long run(Connection connection, Documents documents,
            String range, String serie, Object ns) throws Exception {
        long current;
        Select select;
        Update update;
        Object[] lines;
        Map<String, Object> columns;
        
        if (range == null)
            throw new IocasteException("Numeric range not specified.");

        select = documents.database.get("select");
        ns = (ns != null)? ns = ns.toString() : "";
        if (serie == null) {
            lines = select.run(connection, QUERIES[RANGE], 1, range, ns);
        } else {
            serie = range.concat(serie);
            lines = select.run(connection, QUERIES[RANGE_SERIE], 1, serie, ns);
        }
        
        if (lines == null)
            throw new IocasteException("Range \"%s\" not found.", range);
        
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
