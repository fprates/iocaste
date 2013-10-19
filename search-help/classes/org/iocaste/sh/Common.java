package org.iocaste.sh;

import java.util.Map;

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
    public static final ExtendedObject[] getResultsFrom(String modelname,
            Documents documents, Map<String, ValueRange> criteria) {
        boolean started;
        Query query = new Query();
        
        query.setModel(modelname);
        if (criteria == null || criteria.size() == 0) {
            return documents.select(query);
        } else {
            started = false;
            for (String name : criteria.keySet()) {
                if (started) {
                    query.addIn(name, criteria.values().toArray());
                    continue;
                }
                
                started = true;
                query.andIn(name, criteria.values().toArray());
            }
            
            return documents.select(query);
        }
    }
}
