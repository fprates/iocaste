package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Stack;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.protocol.Message;

public class CreateModels extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        int code;
        String name;
        Stack<String> rollback;
        CreateModel createmodel;
        RemoveModel removemodel;
        DocumentModel[] models = message.get("models");
        Documents documents = getFunction();
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        
        for (DocumentModel model : models)
            prepareElements(connection, documents, model);
        
        code = 0;
        rollback = new Stack<>();
        createmodel = documents.get("create_model"); 
        for (DocumentModel model : models) {
            name = model.getTableName();
            if (name == null)
                continue;
            code = createmodel.createTable(connection, documents, model);
            if (code < 0)
                break;
            rollback.push(name);
        }
        
        if (code < 0) {
            removemodel = documents.get("remove_model");
            while (!rollback.isEmpty()) {
                name = rollback.pop();
                removemodel.removeTable(connection, name);
            }
            return -1;
        }
        
        for (DocumentModel model : models) {
            createmodel.registerModel(connection, documents, model);        
            name = model.getName();
            documents.cache.models.put(name, model);
        }
        return 1;
    }

}
