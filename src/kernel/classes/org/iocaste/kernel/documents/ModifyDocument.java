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
        ExtendedObject object = message.get("object");
        Documents documents = getFunction();
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        
        return run(documents, connection, object);
    }
    
    public int run(Documents documents, Connection connection,
            ExtendedObject object) throws Exception {
        String query, name;
        Object value;
        int nrregs;
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

        value = object.getNS();
        if (value != null) {
            uargs.add(value);
            criteria.add(value);
            iargs.add(value);
        }
        uargs.addAll(criteria);
        nrregs = 0;
        name = model.getName();
        documents = getFunction();
        query = documents.cache.queries.get(name).get("update");
        if (query != null) {
            nrregs = update(connection, query, uargs.toArray());
            if (nrregs > 0)
                return 1;
        }
        
        query = documents.cache.queries.get(name).get("insert");
        if (update(connection, query, iargs.toArray()) <= 0)
            throw new IocasteException("Error on object insert");
        
        return 1;
    }

}
