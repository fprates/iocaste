package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.NameSpace;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class SaveDocument extends AbstractDocumentsHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        ExtendedObject object = message.get("object");
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        return run(connection, documents, object);
    }
    
    public int run(
            Connection connection, Documents documents, ExtendedObject object)
            throws Exception {
        GetDocumentModel getmodel;
        NameSpace[] ns;
        DocumentModel model = object.getModel();
        DocumentModelItem[] itens = model.getItens();
        int i = itens.length;
        Object[] criteria = (i > 0)? new Object[i] : null;
        
        i = 0;
        for (DocumentModelItem item : model.getItens())
            criteria[i++] = object.get(item);
        
        getmodel = documents.get("get_document_model");
        if (model.hasNamespace())
            ns = getmodel.getNS(connection, model);
//        if (ns != null)
//            createNSModel(ns, model);
        
        return update(connection, model.getQuery("insert"), criteria);
    }
}
