package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class SaveDocuments extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        Documents documents;
        SaveDocument savedocument;
        ExtendedObject[] objects;
        Connection connection;
        
        objects = message.get("objects");
        if (objects == null)
            throw new IocasteException("Objects not defined.");
        
        documents = getFunction();
        savedocument = documents.get("save_document");
        connection = documents.database.getDBConnection(message.getSessionid());
        for (ExtendedObject object : objects)
            if (savedocument.run(connection, object) != 1)
                throw new IocasteException("error on saving object.");
        
        return 1;
    }

}
