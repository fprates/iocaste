package org.iocaste.documents;

import org.iocaste.protocol.Iocaste;

public class Update {
    
    /**
     * 
     * @param query
     * @param cache
     * @param criteria
     * @return
     * @throws Exception
     */
    public static final int init(String query, Cache cache,
            Object... criteria) throws Exception {
        QueryInfo queryinfo = Parser.parseQuery(query, criteria, cache);
        
        return new Iocaste(cache.function).update(queryinfo.query, criteria);
    }
}
