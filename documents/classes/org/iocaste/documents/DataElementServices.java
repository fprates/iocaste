package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.Iocaste;

public class DataElementServices {
    private static final byte ELEMENT = 0;
    private static final byte INS_ELEMENT = 1;
    private static final String[] QUERIES = {
        "select * from DOCS003 where ename = ?",
        "insert into DOCS003(ename, decim, lngth, etype, upcas) " +
                "values(?, ?, ?, ?, ?)"
    };
    
    /**
     * 
     * @param iocaste
     * @param name
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final DataElement get(Iocaste iocaste, String name)
            throws Exception {
        Object[] lines;
        DataElement element;
        Map<String, Object> columns;
        
        lines = iocaste.select(QUERIES[ELEMENT], name);
        
        if (lines == null)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        element = new DataElement();
        element.setName(name);
        element.setType(((BigDecimal)columns.get("ETYPE")).intValue());
        element.setLength(((BigDecimal)columns.get("LNGTH")).intValue());
        element.setDecimals(((BigDecimal)columns.get("DECIM")).intValue());
        element.setUpcase((Boolean)columns.get("UPCAS"));
        
        return element;
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @return
     * @throws Exception
     */
    public static final int insert(Iocaste iocaste, DataElement element)
            throws Exception {
        switch (element.getType()) {
        case DataType.DATE:
            element.setLength(10);
            break;
        case DataType.TIME:
            element.setLength(8);
            break;
        }
        
        return iocaste.update(QUERIES[INS_ELEMENT], element.getName(),
                element.getDecimals(),
                element.getLength(),
                element.getType(),
                element.isUpcase());
    }
}
