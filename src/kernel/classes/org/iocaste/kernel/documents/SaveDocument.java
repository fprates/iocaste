package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class SaveDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        ExtendedObject object = message.get("object");
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        return run(connection, object);
    }
    
    public int run(Connection connection, ExtendedObject object)
            throws Exception {
        GetDocumentModel getmodel;
        Object[] criteria;
        DocumentModelItem ns;
        String query, modelname;
        Documents documents;
        Map<String,String> queries;
        DocumentModel model = object.getModel();
        DocumentModelItem[] itens = model.getItens();
        int i = itens.length;

        ns = model.getNamespace();
        if (ns != null)
            i++;

        criteria = (i > 0)? new Object[i] : null;
        i = 0;
        for (DocumentModelItem item : itens)
            criteria[i++] = object.get(item);

        if (ns != null) {
            criteria[i] = object.getNS();
            if (criteria[i] == null)
                criteria[i] = "";
        }
        
        documents = getFunction();
        modelname = model.getName();
        queries = documents.cache.queries.get(modelname);
        if (queries == null) {
            getmodel = documents.get("get_document_model");
            getmodel.run(connection, documents, modelname);
            queries = documents.cache.queries.get(model.getName());
        }
        
        query = queries.get("insert");
        return update(connection, query, criteria);
    }
}
