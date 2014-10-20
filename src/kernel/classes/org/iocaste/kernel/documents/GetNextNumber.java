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
        String range = message.getString("range");
        
        if (range == null)
            throw new IocasteException("Numeric range not specified.");

        documents = getFunction();
        select = documents.database.get("select");
        connection = documents.database.getDBConnection(message.getSessionid());
        lines = select.run(connection, QUERIES[RANGE], 1, range);
        
        if (lines == null)
            throw new IocasteException(new StringBuilder("Range \"").
                    append(range).append("\" not found.").toString());
        
        columns = (Map<String, Object>)lines[0];
        current = ((BigDecimal)columns.get("CRRNT")).longValue() + 1;
        update = documents.database.get("update");
        update.run(connection, QUERIES[UPDATE_RANGE], current, range);
        
        return current;
    }

}
