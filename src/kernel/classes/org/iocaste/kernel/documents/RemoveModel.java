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
        
        documents = getFunction();
        connection = documents.database.getDBConnection(message.getSessionid());
        getmodel = documents.get("get_document_model");
        model = getmodel.run(connection, documents, modelname);
        if (model == null)
            return 0;
        
        for (DocumentModelKey key : model.getKeys()) {
            name = getComposedName(model.getModelItem(key.getModelItemName()));
            if (update(connection, QUERIES[DEL_KEY], name) == 0)
                throw new IocasteException(new StringBuilder(modelname).
                        append(": error on removing key").toString());
        }
        
        for (DocumentModelItem item : model.getItens())
            if (removeModelItem(connection, item) == 0)
                throw new IocasteException(new StringBuilder(modelname).
                        append(": error on removing item").toString());
        
        tablename = model.getTableName();
        if ((tablename != null) &&
                (update(connection, QUERIES[DEL_MODEL_REF], tablename) == 0))
            throw new IocasteException(new StringBuilder(modelname).
                        append(": error on removing model/table reference").
                        toString());
        
        if (update(connection, QUERIES[DEL_MODEL], modelname) == 0)
            throw new IocasteException(new StringBuilder(modelname).
                    append(": error on removing header model data").toString());
        
        documents.cache.queries.remove(modelname);
        documents.cache.removeModel(modelname);
        
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
