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
        CDocumentData data = new CDocumentData();
        data.cdname = message.getst("name");
        data.id = message.get("id");
        data.ns = message.get("ns");
        data.documents = getFunction();
        data.sessionid = message.getSessionid();
        data.connection = data.documents.database.
                getDBConnection(data.sessionid);
        
        return run(data);
    }

    public ComplexDocument run(CDocumentData data) throws Exception {
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
        
        getcmodel = data.documents.get("get_complex_model");
        cmodel = getcmodel.run(data.connection, data.documents, data.cdname);
        if (cmodel == null)
            throw new IocasteException(
                    data.cdname.concat(" complex model undefined."));
        
        getobject = data.documents.get("get_object");
        object = getobject.run(data.connection, data.documents,
                cmodel.getHeader().getName(), data.ns, data.id);
        if (object == null)
            return null;
        
        model = object.getModel();
        headerkey = getModelKey(model);
        if (headerkey == null)
            throw new IocasteException("no valid key found.");

        select = data.documents.get("select_document");
        document = new ComplexDocument(cmodel);
        document.setHeader(object);
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            model = models.get(name);
            query = new Query();
            query.setModel(model.getName());
            query.setNS(data.ns);
            reference = getReferenceItem(model, headerkey);
            query.andEqual(reference.getName(), data.id);
            objects = select.run(data.connection, query);
            if (objects == null)
                continue;
            document.add(objects);
        }
        
        return document;
    }

}

class CDocumentData {
    String cdname, sessionid;
    Object id, ns;
    Documents documents;
    Connection connection;
}