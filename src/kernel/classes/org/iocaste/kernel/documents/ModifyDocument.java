package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class ModifyDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        String query;
        Object value;
        int nrregs;
        Documents documents;
        Connection connection;
        ExtendedObject object = message.get("object");
        DocumentModel model = object.getModel();
        List<Object> criteria = new ArrayList<>();
        List<Object> uargs = new ArrayList<>();
        List<Object> iargs = new ArrayList<>();
        
        for (DocumentModelItem item : model.getItens()) {
            value = object.get(item);
            
            iargs.add(value);
            if (model.isKey(item))
                criteria.add(value);
            else
                uargs.add(value);
        }
        
        uargs.addAll(criteria);
        nrregs = 0;
        query = model.getQuery("update");
        if (query == null)
            return 0;
        
        documents = getFunction();
        connection = documents.database.getDBConnection(message.getSessionid());
        nrregs = update(connection, query, uargs.toArray());
        if (nrregs == 0 && update(connection, model.getQuery("insert"),
                iargs.toArray()) == 0)
            throw new IocasteException("Error on object insert");
        
        return 1;
    }

}
