package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class CreateDataElement extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        DataElement element = message.get("element");
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        return run(connection, element);
    }
    
    public int run(Connection connection, DataElement element) throws Exception
    {
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

        return update(connection, QUERIES[INS_ELEMENT], name,
                element.getDecimals(), length, type, element.isUpcase(),
                element.getAttributeType());
    }

}
