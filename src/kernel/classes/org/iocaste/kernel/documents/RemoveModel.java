package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class RemoveModel extends AbstractDocumentsHandler {

    public static final void recursive(Connection connection,
            Documents documents, DocumentModel model) throws Exception {
        DocumentModel reference;
        String[] tokens;
        ExtendedObject[] objects;
        Query query;
        String field, modelname;
        String name = model.getTableName();
        SelectDocument select = documents.get("select_document");
        GetDocumentModel getmodel = documents.get("get_document_model");
        RemoveModel removemodel = documents.get("remove_model");
        String pkg = model.getPackage();
        
        modelname = model.getName();
        for (DocumentModelItem item : model.getItens()) {
            field = new StringBuilder(modelname).
                    append(".").append(item.getName()).toString();
            query = new Query();
            query.setModel("FOREIGN_KEY");
            query.andEqual("ITEM_NAME", field);
            objects = select.run(connection, query);
            if (objects == null)
                continue;
            for (ExtendedObject object : objects) {
                tokens = object.getst("REFERENCE").split("\\.");
                reference = getmodel.run(
                        connection, documents, tokens[0], true);
                if (reference.getPackage().equals(pkg));
                    recursive(connection, documents, reference);
            }
        }
        
        removemodel.removeTable(connection, name);
    }
    
    @Override
    public Object run(Message message) throws Exception {
        GetDocumentModel getmodel;
        Documents documents;
        String tablename, name;
        String modelname = message.getst("model_name");
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
        
        try {
        update(connection, QUERIES[DEL_MODEL], modelname);
        } catch (Exception e) {
            // avoid any error on mssql.
        }
        documents.cache.queries.remove(modelname);
        documents.cache.models.remove(modelname);
        
        if (tablename == null)
            return 1;
        
        return removeTable(connection, tablename);
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
    
    public int removeTable(Connection connection, String tablename) {
        StringBuilder sb;
        
        sb = new StringBuilder("drop table ").append(tablename);
        try {
            update(connection, sb.toString());
        } catch (Exception e) {
            
        }
        return 1;
    }

}
