package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class SaveComplexDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        SaveDocument save;
        DeleteComplexDocument delete;
        Map<String, DocumentModel> models;
        ExtendedObject[] objects;
        ComplexDocument document = message.get("document");
        ComplexModel cmodel = document.getModel();
        ExtendedObject object = document.getHeader();
        DocumentModel model = object.getModel(); 
        DocumentModelItem modelkey = getModelKey(model);
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        delete = documents.get("delete_complex_document");
        delete.run(
                connection, documents, cmodel.getName(), object.get(modelkey));
        save = documents.get("save_document");
        save.run(connection, object);
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            objects = document.getItems(name);
            for (ExtendedObject item : objects)
                save.run(connection, item);
        }
        
        return null;
    }

}
