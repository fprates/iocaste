package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class RemoveModel extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        GetDocumentModel getmodel;
        Documents documents;
        String tablename, name, query;
        String modelname = message.getString("model_name");
        DocumentModel model;
        Connection connection;
        DocumentModelItem[] items;
        
        documents = getFunction();
        connection = documents.database.getDBConnection(message.getSessionid());
        getmodel = documents.get("get_document_model");
        model = getmodel.run(connection, documents, modelname);
        if (model == null)
            return 0;
        
        for (DocumentModelKey key : model.getKeys()) {
            name = getComposedName(model.getModelItem(key.getModelItemName()));
            update(connection, QUERIES[DEL_KEY], name);
        }
        
        try {
            items = model.getItens();
        } catch (Exception e) {
            /*
             * model is corrupted.
             */
            items = null;
        }
        
        if (items != null)
            for (DocumentModelItem item : items) {
                if (item == null)
                    continue;
                
                try {
                    removeModelItem(connection, item);
                } catch (Exception e) {
                    continue;
                }
            }
        
        tablename = model.getTableName();
        if (tablename != null)
            update(connection, QUERIES[DEL_MODEL_REF], tablename);
        
        update(connection, QUERIES[DEL_MODEL], modelname);
        
        documents.cache.queries.remove(modelname);
        documents.cache.models.remove(modelname);
        
        if (tablename == null)
            return 1;
        
        query = new StringBuilder("drop table ").append(tablename).toString();
        update(connection, query);
        
        return 1;
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @return
     * @throws Exception
     */
    private final int removeModelItem(Connection connection,
            DocumentModelItem item) throws Exception {
        String error;
        String name = getComposedName(item);
        
        update(connection, QUERIES[DEL_FOREIGN], name);
        update(connection, QUERIES[DEL_SH_REF], name);

        error = "there is search help dependence on item ";
        error = new StringBuilder(error).append(name).toString();
        
        if (select(connection, QUERIES[SH_ITEM], 1, name) != null)
            throw new IocasteException(error);

        if (select(connection, QUERIES[SH_HEAD_EXPRT], 1, name) != null)
            throw new IocasteException(error);

        if (update(connection, QUERIES[DEL_ITEM], name) == 0)
            throw new IocasteException("error on removing model item");

        update(connection, QUERIES[DEL_ELEMENT], name);
        
        return 1;
    }

}
