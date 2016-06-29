package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class CreateCModel extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        SaveDocument save;
        ExtendedObject object;
        Map<String, ComplexModelItem> items;
        ComplexModelItem cmodelitem;
        DocumentModel model;
        String cmodelname;
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        GetDocumentModel getmodel = documents.get("get_document_model");
        Connection connection = documents.database.getDBConnection(sessionid);
        ComplexModel cmodel = message.get("cmodel");
        
        object = cmodelHeaderInstance(connection, documents, getmodel, cmodel);
        save = documents.get("save_document");
        save.run(connection, object);
        
        model = getmodel.run(connection, documents, "COMPLEX_MODEL_ITEM");
        items = cmodel.getItems();
        cmodelname = cmodel.getName();
        for (String name : items.keySet()) {
            cmodelitem = items.get(name);
            object = cmodelItemInstance(model, cmodelname, cmodelitem, name);
            save.run(connection, object);
        }
        
        return 1;
    }

}
