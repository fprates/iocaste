package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;

public class NumberRange {
    private static final byte RANGE = 0;
    private static final byte UPDATE_RANGE = 1;
    private static final String[] QUERIES = {
        "select CRRNT from RANGE001 where ident = ?",
        "update RANGE001 set crrnt = ? where ident = ?"
    };
    
    /**
     * 
     * @param name
     * @param cache
     * @throws Exception
     */
    public static void create(String name, Cache cache) throws Exception {
        ExtendedObject range;
        DocumentModel model = Model.get("NUMBER_RANGE", cache);
        DataElement element = model.getModelItem("IDENT").getDataElement();
        int l = element.getLength();
        
        if (name.length() > l)
            throw new IocasteException(new StringBuilder(name).
                    append(" has invalid range name length (").
                    append(l).append(" bytes max).").toString());
        
        range = new ExtendedObject(model);
        range.setValue("IDENT", name);
        range.setValue("CURRENT", 0);
        
        Save.init(range, cache.function);
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

        lines = iocaste.selectUpTo(QUERIES[RANGE], 1, ident);
        
        if (lines == null)
            throw new IocasteException(new StringBuilder("Range \"").
                    append(ident).append("\" not found.").toString());
        
        columns = (Map<String, Object>)lines[0];
        current = ((BigDecimal)columns.get("CRRNT")).longValue() + 1;
        iocaste.update(QUERIES[UPDATE_RANGE], current, ident);
        
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
        Query query = new Query("delete");
        query.setModel("NUMBER_RANGE");
        query.addEqual("IDENT", name);
        return Update.init(query, cache);
    }
}
