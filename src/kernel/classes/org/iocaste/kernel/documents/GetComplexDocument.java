package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetComplexDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        GetComplexModel getcmodel;
        GetObject getobject;
        SelectDocument select;
        DocumentModel model;
        ComplexDocument document;
        ExtendedObject object;
        ExtendedObject[] objects;
        Map<String, DocumentModel> models;
        Query query;
        ComplexModel cmodel;
        DocumentModelItem reference, headerkey;
        String cdname = message.getString("name");
        Object id = message.get("id");
        Object ns = message.get("ns");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        getcmodel = documents.get("get_complex_model");
        cmodel = getcmodel.run(connection, documents, cdname);
        getobject = documents.get("get_object");
        object = getobject.run(connection, documents,
                cmodel.getHeader().getName(), ns, id);
        if (object == null)
            return null;
        
        model = object.getModel();
        headerkey = getModelKey(model);
        if (headerkey == null)
            throw new IocasteException("no valid key found.");

        select = documents.get("select_document");
        document = new ComplexDocument(cmodel);
        document.setHeader(object);
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            model = models.get(name);
            query = new Query();
            query.setModel(model.getName());
            query.setNS(ns);
            reference = getReferenceItem(model, headerkey);
            query.andEqual(reference.getName(), id);
            objects = select.run(connection, query);
            if (objects == null)
                continue;
            document.add(objects);
        }
        
        return document;
    }

}
