package org.iocaste.kernel.documents;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.kernel.database.Select;
import org.iocaste.protocol.Message;

public class GetDataElement extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        String name = message.getString("name");
        
        return run(name, sessionid);
    }

    @SuppressWarnings("unchecked")
    public DataElement run(String name, String sessionid) throws Exception {
        Object[] lines;
        DataElement element;
        Map<String, Object> columns;
        Documents documents;
        Select select;
        Connection connection;
        
        documents = getFunction();
        connection = documents.database.getDBConnection(sessionid);
        documents = getFunction();
        select = documents.get("select");
        lines = select.run(connection, QUERIES[ELEMENT], 0, name);
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
