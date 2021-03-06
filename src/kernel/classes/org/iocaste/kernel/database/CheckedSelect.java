package org.iocaste.kernel.database;

import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class CheckedSelect extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String columns = message.getst("columns");
        String from = message.getst("from");
        String where = message.getst("where");
        Object[] criteria = message.get("criteria");
        Map<String, String[]> ijoin = message.get("inner_join");
        return run(getFunction(), columns, from, where, criteria, ijoin);
    }
    
    public final Object[] run(Database database, String columns, String from,
            String where, Object[] criteria, Map<String, String[]> ijoin)
                throws Exception {
        Select select;
        Object[] results;
        ConnectionState connstate;
        String query;
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
        
        query = sb.toString();
        connstate = database.instance();
        select = database.get("select");
        results = select.run(connstate.connection, query, 0, criteria);
        connstate.connection.commit();
        database.free(connstate);
        
        return results;
    }

}
