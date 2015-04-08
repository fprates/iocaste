package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.NameSpace;
import org.iocaste.protocol.Message;

public class SetNamespace extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        NameSpace[] nss;
        Map<String, String> queries;
        Map<String, Map<String, String>> nsqueries;
        Map<String, DocumentModel> items;
        NamespaceEntry nsentry;
        GetComplexModel getcmodel;
        DocumentModel model;
        ComplexModel cmodel;
        String name = message.getString("name");
        Object value = message.get("value");
        String sessionid = message.getSessionid();
        Documents documents = getFunction();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        getcmodel = documents.get("get_complex_model");
        nsqueries = documents.cache.nsqueries.get(sessionid);
        if (nsqueries == null) {
            nsqueries = new HashMap<>();
            documents.cache.nsqueries.put(sessionid, nsqueries);
        }

        nsentry = new NamespaceEntry(name, value);
        for (String modelname : documents.cache.nsmodels.keySet()) {
            nss = documents.cache.nsmodels.get(modelname);
            for (NameSpace ns : nss) {
                if (!ns.getName().equals(name))
                    continue;
                for (String cmodelname : ns.cmodels) {
                    cmodel = getcmodel.run(connection, documents, cmodelname);
                    model = cmodel.getHeader();
                    queries = documents.parseQueries(nsentry, model);
                    nsqueries.put(model.getName(), queries);
                    
                    items = cmodel.getItems();
                    for (String itemname : items.keySet()) {
                        model = items.get(itemname);
                        queries = documents.parseQueries(nsentry, model);
                        nsqueries.put(model.getName(), queries);
                    }
                }
                
                break;
            }
        }
        
        return null;
    }

}
