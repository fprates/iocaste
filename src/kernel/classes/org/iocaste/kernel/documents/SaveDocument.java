package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.NameSpace;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class SaveDocument extends AbstractDocumentsHandler {
    
    private final void createModel(Connection connection, Documents documents,
            CreateModel createmodel, DocumentModel nsmodel, Object key,
                    NameSpace ns) throws Exception {
        String name;
        
        name = nsmodel.getTableName();
        name = new StringBuilder(key.toString()).append(name).toString();
        nsmodel.setTableName(name);
        createmodel.run(connection, documents, nsmodel, ns);
    }
    
    private final void createNSModel(Connection connection, Documents documents,
            DocumentModel model, NameSpace ns, ExtendedObject object)
                    throws Exception {
        ComplexModel cmodel;
        GetComplexModel getcmodel;
        Object value;
        CreateModel createmodel;
        DocumentModel nsmodel;

        value = null;
        for (DocumentModelKey key : model.getKeys()) {
            value = object.get(key.getModelItemName());
            break;
        }

        if (value == null)
            throw new IocasteException("undefined key for NS model.");
        
        getcmodel = documents.get("get_complex_model");
        createmodel = documents.get("create_model");
        for (String cmodelname : ns.cmodels) {
            cmodel = getcmodel.run(connection, documents, cmodelname);
            if (cmodel == null)
                throw new IocasteException(
                        cmodelname.concat(" is an invalid complex model."));
            
            nsmodel = cmodel.getHeader();
            createModel(connection, documents, createmodel, nsmodel, value, ns);
            
            for (DocumentModel imodel : cmodel.getItems().values())
                createModel(
                        connection, documents, createmodel, imodel, value, ns);
        }
    }
    
    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        ExtendedObject object = message.get("object");
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        return run(sessionid, connection, documents, object);
    }
    
    public int run(String sessionid, Connection connection, Documents documents,
            ExtendedObject object) throws Exception {
        String query;
        NameSpace[] nss;
        DocumentModel model = object.getModel();
        DocumentModelItem[] itens = model.getItens();
        int i = itens.length;
        Object[] criteria = (i > 0)? new Object[i] : null;
        
        i = 0;
        for (DocumentModelItem item : model.getItens())
            criteria[i++] = object.get(item);
        
        nss = documents.cache.nsmodels.get(model.getName());
        if (nss != null)
            for (NameSpace ns : nss)
                createNSModel(connection, documents, model, ns, object);
        
        query = documents.getQuery(sessionid, model, "insert");
        return update(connection, query, criteria);
    }
}
