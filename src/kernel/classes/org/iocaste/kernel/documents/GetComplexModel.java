package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetComplexModel extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        String name = message.getst("name");
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        return run(connection, documents, name);
    }
    
    public ComplexModel run(Connection connection, Documents documents,
            String name) throws Exception {
        int modeltype;
        String modelname;
        DocumentModel model;
        SelectDocument select;
        GetObject getobject;
        GetDocumentModel getmodel;
        ExtendedObject object;
        ExtendedObject[] objects;
        ComplexModel cmodel, _cmodel;
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
        
        for (ExtendedObject item : objects) {
            modeltype = item.geti("MODEL_TYPE");
            modelname = item.getst("MODEL");
            switch (modeltype) {
            case 0:
                model = getmodel.run(connection, documents, modelname);
                if (model == null)
                    throw new IocasteException(new StringBuilder(name).
                            append(" complex model corrupted. Item model ").
                            append(modelname).append(" not found.").toString());
                cmodel.put(item.getst("NAME"), model);
                break;
            case 1:
                _cmodel = run(connection, documents, modelname);
                if (_cmodel == null)
                    throw new IocasteException(new StringBuilder(name).
                            append(" complex model corrupted. Item cmodel ").
                            append(modelname).append(" not found.").toString());
                cmodel.put(item.getst("NAME"), _cmodel);
                break;
            default:
            }
        }
        
        return cmodel;
    }

}
