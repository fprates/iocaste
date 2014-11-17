package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class CreateDataElement extends AbstractDocumentsHandler {
    
    public void prepare(DataElement element) throws Exception {
        
        switch (element.getType()) {
        case DataType.DATE:
            element.setLength(10);
            element.setDecimals(0);
            element.setUpcase(false);
            
            break;
        case DataType.TIME:
            element.setLength(8);
            element.setDecimals(0);
            element.setUpcase(false);
            
            break;
        case DataType.BOOLEAN:
            element.setLength(1);
            element.setDecimals(0);
            element.setUpcase(false);
            
            break;
            
        default:
            if (element.getLength() == 0)
                throw new IocasteException(
                        new StringBuilder("Invalid length for data element ").
                        append(element.getName()).toString());
            break;
        }
    }
    
    @Override
    public Object run(Message message) throws Exception {
        DataElement element = message.get("element");
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        return run(connection, element);
    }
    
    public int run(Connection connection, DataElement element)
            throws Exception {
        return update(connection, QUERIES[INS_ELEMENT],
                element.getName(),
                element.getDecimals(),
                element.getLength(),
                element.getType(),
                element.isUpcase(),
                element.getAttributeType());
    }

}
