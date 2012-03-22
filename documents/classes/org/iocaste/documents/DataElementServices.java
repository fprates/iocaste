package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Iocaste;

public class DataElementServices {
    
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
        
        lines = iocaste.select("select * from docs003 where ename = ?", name);
        
        if (lines.length == 0)
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
    public static final int insert(Iocaste iocaste, DocumentModelItem item)
            throws Exception {
        DataElement dataelement;
        String name, query = "insert into docs003(ename, decim, lngth, " +
                "etype, upcas) values(?, ?, ?, ?, ?)";
        
        dataelement = item.getDataElement();
        name = Documents.getComposedName(item);
        
        return iocaste.update(query, name,
                dataelement.getDecimals(),
                dataelement.getLength(),
                dataelement.getType(),
                dataelement.isUpcase());
    }
}
