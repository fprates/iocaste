package org.iocaste.kernel.documents;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class SaveComplexDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        DeleteDocument delete;
        ModifyDocument modify;
        GetComplexDocument getcdoc;
        Map<String, DocumentModel> models;
        ExtendedObject[] objects;
        ComplexDocument original;
        Map<String, Object> keys;
        ComplexDocument document = message.get("document");
        ComplexModel cmodel = document.getModel();
        CDocumentData data = new CDocumentData();

        data.cdname = document.getModel().getName();
        data.id = document.getKey();
        data.ns = document.getNS();
        data.documents = getFunction();
        data.sessionid = message.getSessionid();
        data.connection = data.documents.database.
                getDBConnection(data.sessionid);
        
        getcdoc = data.documents.get("get_complex_document");
        original = getcdoc.run(data);
        modify = data.documents.get("modify");
        modify.run(data.documents, data.connection, document.getHeader());
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            objects = document.getItems(name);
            for (ExtendedObject item : objects)
                modify.run(data.documents, data.connection, item);
        }
        
        if (original == null)
            return null;
        
        delete = data.documents.get("delete_document");
        for (String name : models.keySet()) {
            objects = original.getItems(name);
            keys = null;
            for (ExtendedObject object : objects) {
                if (keys == null) {
                    keys = new HashMap<>();
                    for (DocumentModelKey key : object.getModel().getKeys()) {
                        name = key.getModelItemName();
                        keys.put(name, null);
                    }
                }
                
                for (String key : keys.keySet())
                    keys.put(key, object.get(key));
                
                if (org.iocaste.documents.common.Documents.
                        readobjects(objects, keys) == null)
                    delete.run(data.documents, data.connection, object);
            }
        }
        
        return null;
    }

}
