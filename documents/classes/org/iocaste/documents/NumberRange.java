package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;

public class NumberRange {
    
    /**
     * 
     * @param name
     * @param cache
     * @throws Exception
     */
    public static void create(String name, Cache cache) throws Exception {
        DocumentModel model = Model.get("NUMBER_RANGE", cache);
        ExtendedObject range = new ExtendedObject(model);
        
        range.setValue("IDENT", name);
        range.setValue("CURRENT", 0);
        
        Query.save(range, cache.function);
    }
    
    /**
     * 
     * @param ident
     * @param function
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static long getCurrent(String ident, Function function)
            throws Exception {
        long current;
        Object[] lines;
        Map<String, Object> columns;
        Iocaste iocaste = new Iocaste(function);
        
        if (ident == null)
            throw new IocasteException("Numeric range not specified.");

        lines = iocaste.select("select crrnt from range001 where ident = ?",
                ident);
        
        if (lines.length == 0)
            throw new IocasteException("Range \""+ident+"\" not found.");
        
        columns = (Map<String, Object>)lines[0];
        current = ((BigDecimal)columns.get("CRRNT")).longValue() + 1;
        iocaste.update("update range001 set crrnt = ? where ident = ?",
                current, ident);
        
        return current;
    }

    /**
     * 
     * @param name
     * @param cache
     * @return
     * @throws Exception
     */
    public static int remove(String name, Cache cache) throws Exception {
        String query = "delete from NUMBER_RANGE where IDENT = ?";
        
        return Query.update(query, cache, name);
    }
}
