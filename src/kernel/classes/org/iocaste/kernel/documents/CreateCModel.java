package org.iocaste.kernel.documents;

import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class CreateCModel extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        SaveDocument save;
        ExtendedObject object;
        Map<String, DocumentModel> items;
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        GetDocumentModel getmodel = documents.get("get_document_model");
        DocumentModel model = getmodel.run("COMPLEX_MODEL", sessionid);
        ComplexModel cmodel = message.get("cmodel");
        String cmodelname = cmodel.getName();
        
        object = new ExtendedObject(model);
        object.set("NAME", cmodelname);
        object.set("MODEL", cmodel.getHeader().getName());
        save = documents.get("save_document");
        save.run(object, sessionid);
        
        model = getmodel.run("COMPLEX_MODEL_ITEM", sessionid);
        items = cmodel.getItems();
        for (String name : items.keySet()) {
            object = new ExtendedObject(model);
            object.set("IDENT", new StringBuilder(cmodelname).
                    append("_").
                    append(name).toString());
            object.set("NAME", name);
            object.set("CMODEL", cmodelname);
            object.set("MODEL", items.get(name).getName());
            save.run(object, sessionid);
        }
        
        return 1;
    }

}