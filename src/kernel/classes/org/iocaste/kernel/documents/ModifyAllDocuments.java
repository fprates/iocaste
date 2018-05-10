package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.List;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class ModifyAllDocuments extends AbstractDocumentsHandler {
    
    private final ExtendedObject[] getArray(Message message) {
        return (ExtendedObject[])message.get("objects");
    }

    @SuppressWarnings("unchecked")
    private final List<ExtendedObject> getCollection(Message message) {
        return (List<ExtendedObject>)message.get("objects");
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Documents documents = getFunction();
        ModifyDocument modify = documents.get("modify");
        int type = message.geti("type");
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        switch (type) {
        case 0:
            for (ExtendedObject object : getArray(message))
                modify.run(documents, connection, object);
            break;
        case 1:
            for (ExtendedObject object : getCollection(message))
                modify.run(documents, connection, object);
            break;
        default:
            throw new IocasteException("invalid type");
        }
        return null;
    }

}
