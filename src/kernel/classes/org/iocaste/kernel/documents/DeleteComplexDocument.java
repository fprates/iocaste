package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class DeleteComplexDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        GetComplexModel getcmodel;
        UpdateDocument update;
        DocumentModelItem headerkey, reference;
        DocumentModel model, header;
        Query query;
        Map<String, DocumentModel> models;
        ComplexModel cmodel;
        String name = message.get("cmodel_name");
        Object id = message.get("id");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        getcmodel = documents.get("get_complex_model");
        cmodel = getcmodel.run(connection, documents, name);
        header = cmodel.getHeader();
        headerkey = getModelKey(header);
        models = cmodel.getItems();
        update = documents.get("update_document");
        for (String item : models.keySet()) {
            model = models.get(item);
            query = new Query("delete");
            query.setModel(model.getName());
            reference = getReferenceItem(model, headerkey);
            query.andEqual(reference.getName(), id);
            update.run(connection, documents, query);
        }
        
        query = new Query("delete");
        query.setModel(header.getName());
        query.andEqual(headerkey.getName(), id);
        return update.run(connection, documents, query);
    }

}
