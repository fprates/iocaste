package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;

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
        element = new DataElement(name);
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
        String name = element.getName();
        int length = element.getLength();
        int type = element.getType();
        
        switch (type) {
        case DataType.DATE:
            length = 10;
            element.setLength(length);
            break;
            
        case DataType.TIME:
            length = 8;
            element.setLength(length);
            break;
            
        default:
            if (length == 0)
                throw new IocasteException(new StringBuilder("Invalid length "
                        + "for data element ").append(name).toString());
            break;
        }
        
        return iocaste.update(QUERIES[INS_ELEMENT], name,
                element.getDecimals(), length, type, element.isUpcase());
    }
}
