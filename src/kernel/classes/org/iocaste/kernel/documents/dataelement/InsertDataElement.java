package org.iocaste.kernel.documents.dataelement;

import java.sql.Connection;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.kernel.documents.AbstractDocumentsHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class InsertDataElement extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        return null;
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
                throw new IocasteException(
                        "Invalid length for data element ".concat(name));
            break;
        }
        
        return update(connection, QUERIES[INS_ELEMENT], name,
                element.getDecimals(), length, type, element.isUpcase(),
                element.getAttributeType());
    }

}
