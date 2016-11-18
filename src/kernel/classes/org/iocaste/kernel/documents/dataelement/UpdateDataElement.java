package org.iocaste.kernel.documents.dataelement;

import java.sql.Connection;

import org.iocaste.documents.common.DataElement;
import org.iocaste.kernel.documents.AbstractDocumentsHandler;
import org.iocaste.kernel.documents.Documents;
import org.iocaste.protocol.Message;

public class UpdateDataElement extends AbstractDocumentsHandler {

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
        return update(connection, QUERIES[UPDATE_ELEMENT],
                element.getDecimals(),
                element.getLength(),
                element.getType(),
                element.isUpcase(),
                element.getName());
    }

}
