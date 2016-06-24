package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetComplexDocument extends AbstractDocumentsHandler {
    private final ExtendedObject[] findItems(CDocumentData data)
            throws Exception {
        Query query;
        DocumentModelItem reference; 

        reference = getReferenceItem(data.model, data.headerkey);
        query = new Query();
        query.setModel(data.model.getName());
        query.setNS(data.ns);
        query.andEqual(reference.getName(), data.id);
        return data.select.run(data.connection, query);
    }
    
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
        DocumentModel model;
        ComplexDocument document;
        ExtendedObject object;
        ExtendedObject[] objects;
        ComplexModelItem cmodelitem;
        Map<String, ComplexModelItem> models;
        ComplexModel cmodel;
        CDocumentData _data;
        
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
        data.headerkey = getModelKey(model);
        if (data.headerkey == null)
            throw new IocasteException("no valid key found.");

        data.select = data.documents.get("select_document");
        document = new ComplexDocument(cmodel);
        document.setHeader(object);
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            cmodelitem = models.get(name);
            if (cmodelitem.model != null) {
                data.model = cmodelitem.model;
                objects = findItems(data);
                if (objects == null)
                    continue;
                document.add(objects);
                continue;
            }
            
            data.model = cmodelitem.cmodel.getHeader();
            objects = findItems(data);
            if (objects == null)
                continue;
            for (ExtendedObject id : objects) {
                _data = new CDocumentData();
                _data.cdname = cmodelitem.cmodel.getName();
                for (DocumentModelKey key : data.model.getKeys()) {
                    _data.id = id.get(key.getModelItemName());
                    break;
                }
                _data.ns = data.ns;
                _data.documents = data.documents;
                _data.sessionid = data.sessionid;
                _data.connection = data.connection;
                document.add(run(_data));
            }
        }
        
        return document;
    }

}

class CDocumentData {
    String cdname, sessionid;
    Object id, ns;
    Documents documents;
    Connection connection;
    SelectDocument select;
    DocumentModelItem headerkey;
    DocumentModel model;
}