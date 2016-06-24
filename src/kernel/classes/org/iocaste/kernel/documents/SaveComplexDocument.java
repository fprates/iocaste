package org.iocaste.kernel.documents;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class SaveComplexDocument extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        DeleteDocument delete;
        ModifyDocument modify;
        GetComplexDocument getcdoc;
        Map<String, ComplexModelItem> models;
        ExtendedObject[] nobjects, oobjects;
        ComplexDocument original;
        Map<String, Object> keys;
        ComplexModelItem cmodelitem;
        ComplexDocument document = message.get("document");
        ComplexModel cmodel = document.getModel();
        CDocumentData data = new CDocumentData();

        data.cdname = cmodel.getName();
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
            cmodelitem = models.get(name);
            if (cmodelitem.model == null)
                continue;
            nobjects = document.getItems(name);
            for (ExtendedObject item : nobjects)
                modify.run(data.documents, data.connection, item);
        }
        
        if (original == null)
            return null;
        
        delete = data.documents.get("delete_document");
        keys = null;
        for (String name : models.keySet()) {
            cmodelitem = models.get(name);
            if (cmodelitem.model == null)
                continue;
            oobjects = original.getItems(name);
            nobjects = document.getItems(name);
            if (keys != null)
                keys.clear();
            for (ExtendedObject object : oobjects) {
                if (keys == null) {
                    keys = new HashMap<>();
                    for (DocumentModelKey key : object.getModel().getKeys()) {
                        name = key.getModelItemName();
                        keys.put(name, null);
                    }
                }
                
                for (String key : keys.keySet())
                    keys.put(key, object.get(key));
                
                if ((nobjects.length == 0) ||
                        (org.iocaste.documents.common.Documents.
                        readobjects(nobjects, keys) == null))
                    delete.run(data.documents, data.connection, object);
            }
        }
        
        return null;
    }

}
