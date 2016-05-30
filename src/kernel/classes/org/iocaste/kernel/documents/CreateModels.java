package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Stack;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class CreateModels extends AbstractDocumentsHandler {

    private final void recursiveRemove(Connection connection,
            Documents documents, DocumentModel model) throws Exception {
        DocumentModel reference;
        String name = model.getTableName();
        SelectDocument select = documents.get("select_document");
        GetDocumentModel getmodel = documents.get("get_document_model");
        RemoveModel removemodel = documents.get("remove_model");
        String[] tokens;
        ExtendedObject[] objects;
        Query query;
        String field, modelname;
        
        modelname = model.getName();
        for (DocumentModelItem item : model.getItens()) {
            field = new StringBuilder(modelname).
                    append(".").append(item.getName()).toString();
            query = new Query();
            query.setModel("FOREIGN_KEY");
            query.andEqual("ITEM_NAME", field);
            objects = select.run(connection, query);
            if (objects == null)
                continue;
            for (ExtendedObject object : objects) {
                tokens = object.getst("REFERENCE").split("\\.");
                reference = getmodel.run(connection, documents, tokens[0]);
                recursiveRemove(connection, documents, reference);
            }
        }
        
        removemodel.removeTable(connection, name);
    }
    
    @Override
    public Object run(Message message) throws Exception {
        int code;
        String name;
        Stack<String> rollback;
        CreateModel createmodel;
        RemoveModel removemodel;
        GetDocumentModel getmodel;
        DocumentModel oldmodel;
        DocumentModel[] models = message.get("models");
        Documents documents = getFunction();
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        
        for (DocumentModel model : models)
            prepareElements(connection, documents, model);
        
        code = 0;
        rollback = new Stack<>();
        getmodel = documents.get("get_document_model");
        createmodel = documents.get("create_model"); 
        for (DocumentModel model : models) {
            name = model.getTableName();
            if (name == null)
                continue;
            oldmodel = getmodel.run(
                    connection, documents, model.getName(), true);
            if (oldmodel != null)
                recursiveRemove(connection, documents, oldmodel);
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
