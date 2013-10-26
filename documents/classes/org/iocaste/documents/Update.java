package org.iocaste.documents;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;

public class Update {
    
    /**
     * 
     * @param query
     * @param cache
     * @return
     * @throws Exception
     */
    public static final int init(Query query, Cache cache) throws Exception {
        List<Object> values;
        
        if (query.getStatement() == null)
            throw new IocasteException("query statement not defined.");
        
        values = new ArrayList<>();
        String sql = Parser.parseQuery(query, cache, values);
        return new Iocaste(cache.function).update(sql, values);
    }
}
