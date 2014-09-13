package org.iocaste.kernel.database;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.common.IocasteException;
import org.iocaste.kernel.common.Message;

public class CheckedSelect extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Database database;
        Select select;
        Object[] results;
        Connection connection;
        String query, columns = message.getString("columns");
        String from = message.getString("from");
        String where = message.getString("where");
        Object[] criteria = message.get("criteria");
        Map<String, String[]> ijoin = message.get("inner_join");
        StringBuilder sb = new StringBuilder("select ");
        
        sb.append((columns == null)? "*" : columns);
        if (from == null)
            throw new IocasteException("Table not specified.");
        
        sb.append(" from ").append(from);
        if (ijoin != null)
            for (String jointable : ijoin.keySet()) {
                sb.append(" inner join ").
                        append(jointable).
                        append(" on ");
                for (String clause : ijoin.get(jointable))
                    sb.append(clause);
            }
        
        if (where != null)
            sb.append(" where ").append(where);
        
        database = getFunction();
        query = sb.toString();
        connection = database.instance();
        select = database.get("select");
        results = select.run(connection, query, 0, criteria);
        connection.close();
        
        return results;
    }

}
