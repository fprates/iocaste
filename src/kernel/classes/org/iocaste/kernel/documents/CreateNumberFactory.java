package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class CreateNumberFactory extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        ExtendedObject range;
        SaveDocument save;
        String name = message.getString("name");
        Documents documents = getFunction();
        GetDocumentModel modelget = documents.get("get_document_model");
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        DocumentModel model = modelget.run(
                connection, documents, "NUMBER_RANGE");
        DataElement element = model.getModelItem("IDENT").getDataElement();
        int l = element.getLength();
        
        if (name.length() > l)
            throw new IocasteException(new StringBuilder(name).
                    append(" has invalid range name length (").
                    append(l).append(" bytes max).").toString());
        
        range = new ExtendedObject(model);
        range.set("IDENT", name);
        range.set("CURRENT", 0);
        
        save = documents.get("save_document");
        return save.run(connection, range);
    }

}
