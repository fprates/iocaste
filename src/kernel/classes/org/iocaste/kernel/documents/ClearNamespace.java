package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.NameSpace;
import org.iocaste.protocol.Message;

public class ClearNamespace extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        NameSpace[] nss;
        Map<String, Map<String, String>> nsqueries;
        Map<String, DocumentModel> items;
        GetComplexModel getcmodel;
        DocumentModel model;
        ComplexModel cmodel;
        String name = message.getString("name");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        getcmodel = documents.get("get_complex_model");
        nsqueries = documents.cache.nsqueries.get(sessionid);

        for (String modelname : documents.cache.nsmodels.keySet()) {
            nss = documents.cache.nsmodels.get(modelname);
            for (NameSpace ns : nss) {
                if (!ns.getName().equals(name))
                    continue;
                for (String cmodelname : ns.cmodels) {
                    cmodel = getcmodel.run(connection, documents, cmodelname);
                    model = cmodel.getHeader();
                    nsqueries.remove(model.getName());
                    
                    items = cmodel.getItems();
                    for (String itemname : items.keySet()) {
                        model = items.get(itemname);
                        nsqueries.remove(model.getName());
                    }
                }
                
                break;
            }
        }
        
        return null;
    }

}
