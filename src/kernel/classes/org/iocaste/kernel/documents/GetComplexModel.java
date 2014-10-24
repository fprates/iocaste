package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;

public class GetComplexModel extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        String name = message.getString("name");
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        return run(connection, documents, name);
    }
    
    public ComplexModel run(Connection connection, Documents documents,
            String name) throws Exception {
        SelectDocument select;
        GetObject getobject;
        GetDocumentModel getmodel;
        ExtendedObject object;
        ExtendedObject[] objects;
        ComplexModel cmodel;
        Query query;
        
        getobject = documents.get("get_object");
        getmodel = documents.get("get_document_model");
        object = getobject.run(connection, documents, "COMPLEX_MODEL", name);
        if (object == null)
            return null;
        
        cmodel = new ComplexModel(name);
        cmodel.setHeader(
                getmodel.run(connection, documents, object.getst("MODEL")));
        
        select = documents.get("select_document");
        query = new Query();
        query.setModel("COMPLEX_MODEL_ITEM");
        query.andEqual("CMODEL", name);
        objects = select.run(connection, query);
        if (objects == null)
            return cmodel;
        
        for (ExtendedObject item : objects)
            cmodel.put(item.getst("NAME"), getmodel.run(
                    connection, documents, item.getst("MODEL")));
        
        return cmodel;
    }

}