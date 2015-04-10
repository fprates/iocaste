package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.NameSpace;
import org.iocaste.protocol.Message;

public class CreateNameSpace extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        ExtendedObject object;
        GetDocumentModel getmodel;
        SaveDocument save;
        Connection connection;
        String nsname;
        int i;
        DocumentModel model;
        String sessionid = message.getSessionid();
        NameSpace ns = message.get("namespace");
        Documents documents = getFunction();
        
        getmodel = documents.get("get_document_model");
        save = documents.get("save_document");
        connection = documents.database.getDBConnection(sessionid);
        nsname = ns.getName();
        model = getmodel.run(connection, documents, "NS_HEAD");
        object = new ExtendedObject(model);
        object.set("NAME", nsname);
        object.set("MODEL", ns.keymodel);
        save.run(connection, object);
        
        model = getmodel.run(connection, documents, "NS_MODELS");
        i = 0;
        for (String name : ns.cmodels) {
            object = new ExtendedObject(model);
            object.set("ITEM", String.format("%s%03d", nsname, i));
            object.set("NAMESPACE", nsname);
            object.set("COMPLEX_MODEL", name);
            save.run(connection, object);
        }
        
        return null;
    }

}
