package org.iocaste.sh;

import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;

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
        ValueRange range;
        Query query = new Query();
        
        query.setModel(modelname);
        if ((criteria == null) || (criteria.size() == 0))
            return documents.select(query);
        
        for (String name : criteria.keySet()) {
            range = criteria.get(name);
            for (ValueRangeItem rangeitem : range.getItens()) {
                switch (rangeitem.getOption()) {
                case BT:
                    query.andGE(name, rangeitem.getLow());
                    query.andLE(name, rangeitem.getHigh());
                    break;
                case CP:
                    query.andLike(name, rangeitem.getLow().toString().
                            replaceAll("[*]", "%"));
                    break;
                case EQ:
                    query.andEqual(name, rangeitem.getLow());
                    break;
                case GE:
                    query.andGE(name, rangeitem.getLow());
                    break;
                case GT:
                    query.andGT(name, rangeitem.getLow());
                    break;
                case LE:
                    query.andLE(name, rangeitem.getLow());
                    break;
                case LT:
                    query.andLT(name, rangeitem.getLow());
                    break;
                case NE:
                    query.andNot(name, rangeitem.getLow());
                    break;
                }
            }
        }
        
        return documents.select(query);
    }
}
