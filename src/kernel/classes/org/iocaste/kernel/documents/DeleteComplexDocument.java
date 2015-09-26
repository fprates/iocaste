package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class DeleteComplexDocument extends AbstractDocumentsHandler {
    @Override
    public Object run(Message message) throws Exception {
        DeleteComplexDocumentData data;
        String name = message.get("cmodel_name");
        Object id = message.get("id");
        Object ns = message.get("ns");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        data = new DeleteComplexDocumentData();
        data.connection = connection;
        data.documents = documents;
        data.cmodel = name;
        data.key = id;
        data.ns = ns;
        return run(data);
    }

    public Object run(DeleteComplexDocumentData data) throws Exception {
        GetComplexModel getcmodel;
        UpdateDocument update;
        DocumentModelItem headerkey, reference;
        DocumentModel model, header;
        Query query;
        Map<String, DocumentModel> models;
        ComplexModel cmodel;
        int err;
        
        getcmodel = data.documents.get("get_complex_model");
        cmodel = getcmodel.run(data.connection, data.documents, data.cmodel);
        header = cmodel.getHeader();
        headerkey = getModelKey(header);
        models = cmodel.getItems();
        update = data.documents.get("update_document");
        for (String item : models.keySet()) {
            model = models.get(item);
            query = new Query("delete");
            query.setModel(model.getName());
            query.setNS(data.ns);
            reference = getReferenceItem(model, headerkey);
            query.andEqual(reference.getName(), data.key);
            err = update.run(data.connection, data.documents, query);
            if (err >= 0)
                continue;
            throw new IocasteException(new StringBuilder(
                    "erro updating complex model ").append(cmodel.getName()).
                    toString());
        }
        
        query = new Query("delete");
        query.setModel(header.getName());
        query.andEqual(headerkey.getName(), data.key);
        query.setNS(data.ns);
        return update.run(data.connection, data.documents, query);
    }
}
