package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class CreateNumberFactory extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        ExtendedObject object;
        SaveDocument save;
        String name, sessionid;
        Map<String, Long> series;
        Documents documents;
        GetDocumentModel modelget;
        Connection connection;
        DocumentModel model;
        DataElement element;
        int l;

        documents = getFunction();
        sessionid = message.getSessionid();
        modelget = documents.get("get_document_model");
        connection = documents.database.getDBConnection(sessionid);
        model = modelget.run(connection, documents, "NUMBER_RANGE");
        element = model.getModelItem("IDENT").getDataElement();
        
        l = element.getLength();
        name = message.getString("name");
        if (name.length() > l)
            throw new IocasteException(new StringBuilder(name).
                    append(" has invalid range name length (").
                    append(l).append(" bytes max).").toString());

        object = new ExtendedObject(model);
        object.set("IDENT", name);
        object.set("CURRENT", 0);
        
        save = documents.get("save_document");
        save.run(sessionid, connection, documents, object);
        
        series = message.get("series");
        if (series != null) {
            model = modelget.run(connection, documents, "NUMBER_SERIES");
            for (String serie : series.keySet()) {
                object = new ExtendedObject(model);
                object.set("SERIE", name.concat(serie));
                object.set("RANGE", name);
                object.set("CURRENT", series.get(serie));
                save.run(sessionid, connection, documents, object);
            }
        }
        
        return 1;
    }

}
