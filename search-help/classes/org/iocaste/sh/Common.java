package org.iocaste.sh;

import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.ValueRange;

public class Common {
    
    /**
     * 
     * @param sh
     * @param function
     * @param criteria
     * @return
     * @throws Exception
     */
    public static final ExtendedObject[] getResultsFrom(String modelname,
            Documents documents, Map<String, ValueRange> criteria)
                    throws Exception {
        boolean started;
        StringBuilder sb = new StringBuilder("from ").append(modelname);
        
        if (criteria == null || criteria.size() == 0) {
            return documents.select(sb.toString());
        } else {
            started = false;
            sb.append(" where");
            for (String name : criteria.keySet()) {
                if (started)
                    sb.append(" and");
                else
                    started = true;
                
                sb.append(" ").append(name).append(" in ?");
            }
            return documents.select(sb.toString(), criteria.values().toArray());
        }
    }
}
