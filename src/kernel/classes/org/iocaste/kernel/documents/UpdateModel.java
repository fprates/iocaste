package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class UpdateModel extends AbstractDocumentsHandler {
    
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
    
    private final int removeTableColumn(Connection connection,
            DocumentModelItem item) throws Exception {
        String fieldname = item.getTableFieldName();
        String tablename = item.getDocumentModel().getTableName();
        String query = new StringBuilder("alter table ").append(tablename).
                append(" drop column ").append(fieldname).toString();
        
        return update(connection, query);
    }

    @Override
    public Object run(Message message) throws Exception {
        InsertDataElement insert;
        GetDocumentModel getmodel;
        DocumentModel oldmodel;
        DocumentModel model = message.get("model");
        String name = model.getName();
        Documents documents = getFunction();
        String refstmt = getReferenceStatement(documents);
        String dbtype = getSystemParameter(documents, "dbtype");
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        
        getmodel = documents.get("get_document_model");
        oldmodel = getmodel.run(connection, documents, name);
        insert = documents.get("insert_data_element");
        for (DocumentModelItem item : model.getItens()) {
            if (!oldmodel.contains(item)) {
                insert.run(connection, item.getDataElement());                
                if (insertModelItem(connection, item) == 0)
                    throw new IocasteException("error on model insert");
                
                if (item.getTableFieldName() != null)
                    addTableColumn(connection, refstmt, item, dbtype);
            } else {
                if (updateModelItem(connection, item, oldmodel) == 0)
                    throw new IocasteException("error on model update");
            }
        }
        
        for (DocumentModelItem olditem : oldmodel.getItens()) {
            if (model.contains(olditem))
                continue;
            
            if (removeModelItem(connection, olditem) == 0)
                throw new IocasteException("error on remove model item");
            
            if (olditem.getTableFieldName() != null)
                if (removeTableColumn(connection, olditem) == 0)
                    throw new IocasteException("error on remove table column");
        }
        
        documents.parseQueries(model);
        documents.cache.models.remove(name);
        documents.cache.models.put(name, model);
        
        return 1;
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @param oldmodel
     * @return
     * @throws Exception
     */
    private final int updateModelItem(Connection connection,
            DocumentModelItem item, DocumentModel oldmodel) throws Exception {
        String query, shname;
        StringBuilder sb;
        DataElement ddelement;
        Object[] criteria;
        DocumentModel model = item.getDocumentModel();
        DocumentModelItem reference, olditem = oldmodel.
                getModelItem(item.getName());
        String tablename = model.getTableName();
        String oldfieldname = olditem.getTableFieldName();
        String fieldname = item.getTableFieldName();
        Documents documents = getFunction();
        String dbtype = getSystemParameter(documents, "dbtype");
        
        update(connection, QUERIES[DEL_SH_REF], getComposedName(olditem));
        
        /*
         * renomeia campo da tabela
         */
        sb = new StringBuilder("alter table ").append(tablename);
        switch (dbtype) {
        case "hsqldb":
            query = sb.append(" alter column ").toString();
            break;
        default:
            query = sb.append(" modify column ").toString();
            break;
        }
        
        if (!fieldname.equals(oldfieldname)) {
            sb = new StringBuilder(query).
                    append(oldfieldname).
                    append(" rename to ").
                    append(fieldname);
            
            update(connection, sb.toString());
        }
        
        /*
         * atualização das característica dos itens da tabela
         */
        sb = new StringBuilder(query);
        sb.append(fieldname);
        
        ddelement = item.getDataElement();
        setTableFieldsString(sb, ddelement, dbtype);
        
        query = sb.toString();
        update(connection, query);
        
        reference = item.getReference();
        if (reference != null) {
            if (olditem.getReference() == null) {
                query = new StringBuilder("alter table ").
                        append(tablename).
                        append(" add foreign key (").
                        append(item.getTableFieldName()).
                        append(") references ").
                        append(reference.getDocumentModel().getTableName()).
                        append("(").
                        append(reference.getTableFieldName()).
                        append(")").toString();
                
                update(connection, query);
            }
        } else {
            if (olditem.getReference() != null) {
                query = new StringBuilder("alter table").
                        append(tablename).
                        append(" drop constraint ").
                        append(item.getTableFieldName()).toString();
                
                update(connection, query);
            }
        }
        
        /*
         * atualização do modelo
         */
        criteria = new Object[5];
        
        criteria[0] = ddelement.getDecimals();
        criteria[1] = ddelement.getLength();
        criteria[2] = ddelement.getType();
        criteria[3] = ddelement.isUpcase();
        criteria[4] = ddelement.getName();

        if (update(connection, QUERIES[UPDATE_ELEMENT], criteria) == 0)
            throw new IocasteException(
                    "error on update data element");
        
        criteria = new Object[7];
        
        criteria[0] = model.getName();
        criteria[1] = item.getIndex();
        criteria[2] = item.getTableFieldName();
        criteria[3] = ddelement.getName();
        criteria[4] = item.getAttributeName();
        criteria[5] = (reference == null)?
                null : getComposedName(reference);
        criteria[6] = getComposedName(item);
        
        if (update(connection, QUERIES[UPDATE_ITEM], criteria) == 0)
            throw new IocasteException(
                    "error on update model item");
        
        shname = item.getSearchHelp();
        if (Documents.isInitial(shname))
            return 1;
        
        if (update(connection, QUERIES[INS_SH_REF], criteria[6], shname) == 0)
            throw new IocasteException(
                    "error on insert sh reference");
        
        return 1;
    }

}
