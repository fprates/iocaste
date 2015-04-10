package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class CreateCModel extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        SaveDocument save;
        ExtendedObject object;
        Map<String, DocumentModel> items;
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        GetDocumentModel getmodel = documents.get("get_document_model");
        Connection connection = documents.database.getDBConnection(sessionid);
        DocumentModel model = getmodel.run(
                connection, documents, "COMPLEX_MODEL");
        ComplexModel cmodel = message.get("cmodel");
        String cmodelname = cmodel.getName();
        
        object = new ExtendedObject(model);
        object.set("NAME", cmodelname);
        object.set("MODEL", cmodel.getHeader().getName());
        save = documents.get("save_document");
        save.run(connection, object);
        
        model = getmodel.run(connection, documents, "COMPLEX_MODEL_ITEM");
        items = cmodel.getItems();
        for (String name : items.keySet()) {
            object = new ExtendedObject(model);
            object.set("IDENT", new StringBuilder(cmodelname).
                    append("_").
                    append(name).toString());
            object.set("NAME", name);
            object.set("CMODEL", cmodelname);
            object.set("MODEL", items.get(name).getName());
            save.run(connection, object);
        }
        
        return 1;
    }

}
