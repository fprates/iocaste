package org.iocaste.kernel.documents;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.protocol.Message;

public class GetDataElement extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        String name = message.getString("name");
        
        setSessionid(message.getSessionid());
        return run(name);
    }

    @SuppressWarnings("unchecked")
    public DataElement run(String name) throws Exception {
        Object[] lines;
        DataElement element;
        Map<String, Object> columns;
        
        lines = select(QUERIES[ELEMENT], 0, name);
        if (lines == null)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        element = new DataElement(name);
        element.setType(((BigDecimal)columns.get("ETYPE")).intValue());
        element.setLength(((BigDecimal)columns.get("LNGTH")).intValue());
        element.setDecimals(((BigDecimal)columns.get("DECIM")).intValue());
        element.setAttributeType(((BigDecimal)columns.get("ATYPE")).intValue());
        element.setUpcase((Boolean)columns.get("UPCAS"));
        
        return element;
    }

}
