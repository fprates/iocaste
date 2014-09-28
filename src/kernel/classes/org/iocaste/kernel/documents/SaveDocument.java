package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.database.Update;
import org.iocaste.protocol.Message;

public class SaveDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        ExtendedObject object = message.get("object");
        
        return run(object, sessionid);
    }
    
    public int run(ExtendedObject object, String sessionid) throws Exception {
        Documents documents;
        Update update;
        Connection connection;
        DocumentModel model = object.getModel();
        DocumentModelItem[] itens = model.getItens();
        int i = itens.length;
        Object[] criteria = (i > 0)? new Object[i] : null;
        
        i = 0;
        for (DocumentModelItem item : model.getItens())
            criteria[i++] = object.get(item);
        
        documents = getFunction();
        update = documents.get("update");
        connection = documents.database.getDBConnection(sessionid);
        return update.run(connection, model.getQuery("insert"), criteria);
    }
}
