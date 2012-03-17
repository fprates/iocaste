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
     * @param function
     * @param queries
     * @throws Exception
     */
    public static void create(String name, Function function, Map<String,
            Map<String, String>> queries) throws Exception {
        DocumentModel model = Model.get("NUMBER_RANGE", function, queries);
        ExtendedObject range = new ExtendedObject(model);
        
        range.setValue("IDENT", name);
        
        Query.save(range, function);
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

}
