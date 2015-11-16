package org.iocaste.sh;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.ValueRange;

public class Common {
    
    /**
     * 
     * @param sh
     * @param function
     * @param criteria
     * @return
     */
    public static final ExtendedObject[] getResultsFrom(Documents documents,
            Context context, Object ns) {
        ValueRange range;
        Query query = new Query();
        
        query.setModel(context.model.getName());
        query.setNS(ns);
        if ((context.criteria == null) || (context.criteria.size() == 0))
            return documents.select(query);
        
        for (String name : context.criteria.keySet()) {
            range = context.criteria.get(name);
            query.andIn(name, range);
        }
        
        return documents.select(query);
    }
}
