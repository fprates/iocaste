package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class ComplexModelUpdate extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        ExtendedObject object;
        ModifyDocument modify;
        Map<String, ComplexModelItem> items, oitems;
        ComplexModel original;
        GetDocumentModel getmodel;
        ComplexModelItem cmodelitem;
        DocumentModel model;
        String cmodelname;
        DeleteDocument delete;
        ComplexModel cmodel = message.get("complex_model");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        GetComplexModel getcmodel = documents.get("get_complex_model");
        
        original = getcmodel.run(connection, documents, cmodel.getName());
        if (original == null)
            throw new IocasteException("cmodel doesn't exist");
        
        getmodel = documents.get("get_document_model");
        object = cmodelHeaderInstance(connection, documents, getmodel, cmodel);
        modify = documents.get("modify");
        modify.run(documents, connection, object);

        model = getmodel.run(connection, documents, "COMPLEX_MODEL_ITEM");
        items = cmodel.getItems();
        cmodelname = cmodel.getName();
        for (String name : items.keySet()) {
            cmodelitem = items.get(name);
            object = cmodelItemInstance(model, cmodelname, cmodelitem, name);
            modify.run(documents, connection, object);
        }

        oitems = original.getItems();
        delete = documents.get("delete_document");
        for (String name : oitems.keySet()) {
            if (items.containsKey(name))
                continue;
            cmodelitem = items.get(name);
            object = cmodelItemInstance(model, cmodelname, cmodelitem, name);
            delete.run(documents, connection, object);
        }
        
        return 1;
    }

}
