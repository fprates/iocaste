package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;

public class NumberRange {
    
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
            throw new Exception("Numeric range not specified.");

        lines = iocaste.select("select crrnt from range001 where ident = ?",
                new Object[] {ident});
        
        if (lines.length == 0)
            throw new Exception("Range \""+ident+"\" not found.");
        
        columns = (Map<String, Object>)lines[0];
        current = ((BigDecimal)columns.get("CRRNT")).longValue() + 1;
        iocaste.update("update range001 set crrnt = ? where ident = ?",
                new Object[] {current, ident});
        iocaste.commit();
        
        return current;
    }

}
