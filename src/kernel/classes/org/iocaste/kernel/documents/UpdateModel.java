package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.kernel.common.AlterTable;
import org.iocaste.kernel.common.Table;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class UpdateModel extends AbstractDocumentsHandler {

    private void dbupdate(UpdateData data) throws Exception {
        boolean namespaced, initial;
        DocumentModel oldmodel;
        List<String> statements;
        String name;
        DocumentModelItem ns, oldns;
        DataElement element;

        statements = new ArrayList<>();
        name = data.model.getName();
        oldmodel = data.getmodel.run(data.connection, data.documents, name);
        oldns = oldmodel.getNamespace();
        ns = data.model.getNamespace();
        nsupdate(data, statements, oldns, ns);
        namespaced = (statements.size() > 0);
        
        initial = true;
        data.table.clear();
        for (DocumentModelKey key : data.model.getKeys()) {
            name = key.getModelItemName();
            
            data.item = data.model.getModelItem(name);
            data.olditem = oldmodel.getModelItem(name);
            if (data.olditem != null) {
                if (!namespaced)
                    continue;
                data.table.pkconstraint(
                        "pk_".concat(data.tablename),
                        data.item.getTableFieldName());
                initial = false;
                continue;
            }
            
            if (data.item.getDataElement() == null)
                retrieveDataElement(data);
            
            element = data.item.getDataElement();
            data.table.key(
                    data.item.getTableFieldName(),
                    element.getType(),
                    element.getLength());
            initial = false;
        }

        /*
         * remove campos
         */
        if (oldmodel.getTableName() != null)
            for (DocumentModelItem olditem : oldmodel.getItens()) {
                if (data.model.contains(olditem))
                    continue;
                
                if (olditem.getTableFieldName() == null)
                    continue;
                
                data.table.drop(olditem.getTableFieldName());
                initial = false;
            }
        
        /*
         * atualiza campos
         */
        for (DocumentModelItem item : data.model.getItens()) {
            if (data.model.isKey(item))
                continue;
            
            data.item = item;
            data.element = data.item.getDataElement();
            if (data.element == null)
                retrieveDataElement(data);

            data.fieldname = item.getTableFieldName();
            data.reference = item.getReference();
            data.olditem = oldmodel.getModelItem(data.item.getName());
            if (data.olditem != null)
                data.oldfieldname = data.olditem.getTableFieldName();
            if ((data.olditem == null) || (data.oldfieldname == null)) {
                if (data.item.getTableFieldName() == null)
                    continue;
                addTableColumn(statements, data);
            } else {
                updateTable(data);
            }
            
            initial = false;
        }
        
        if (!initial)
            data.altertable.compose(statements, data.table);
        
        for (String stmt : statements)
            update(data.connection, stmt);
    }
    
    private final DataElement getElementByReference(UpdateData data,
            DocumentModelItem reference) throws Exception {
        DocumentModel model;
        
        if (!reference.isDummy())
            return reference.getDataElement();
        
        model = data.getmodel.run(data.connection, data.documents,
                reference.getDocumentModel().getName());
        return model.getModelItem(reference.getName()).getDataElement();
    }
    
    private final void nsupdate(UpdateData data, List<String> statements,
            DocumentModelItem oldns, DocumentModelItem ns) {
        String nstablefield, tablename;
        DataElement element;
        
        if ((ns != null) && (oldns == null)) {
            nstablefield = ns.getTableFieldName();
            if (nstablefield == null)
                return;
            tablename = new StringBuilder("pk_").
                    append(data.table.getName()).toString();
            data.table.dropkey(tablename);
            data.altertable.compose(statements, data.table);

            data.table.clear();
            element = ns.getDataElement();
            data.table.key(
                    nstablefield, element.getType(), element.getLength());
        }
        
//        if ((ns == null) && (oldns != null)) {
//            nstablefield = oldns.getTableFieldName();
//            if (nstablefield == null)
//                return;
//            element = oldns.getDataElement();
//            table.drop(nstablefield);
//        }
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
    
    private final void retrieveDataElement(UpdateData data) throws Exception {
        DocumentModelItem reference = data.item.getReference();
        
        if (reference == null)
            return;
        data.item.setDataElement(getElementByReference(data, reference));
    }
    
    @Override
    public Object run(Message message) throws Exception {
        DataElement element;
        DocumentModelItem namespace;
        RemoveModel removemodel;
        CreateModel createmodel;
        DocumentModel oldmodel;
        Map<String, String> queries;
        InsertDataElement insert;
        UpdateData data;
        String name, itemname, oldtablename;
        String sessionid = message.getSessionid();

        data = new UpdateData();
        data.documents = getFunction();
        data.model = message.get("model");
        data.tablename = data.model.getTableName();
        data.connection = data.documents.database.getDBConnection(sessionid);
        data.getmodel = data.documents.get("get_document_model");
        data.dbtype = getSystemParameter(data.documents, "dbtype");
        data.table = new Table(data.tablename);
        data.altertable = new AlterTable(data.dbtype);
        
        prepareElements(data.connection, data.documents, data.model);

        name = data.model.getName();
        oldmodel = data.getmodel.run(data.connection, data.documents, name);
        oldtablename = oldmodel.getTableName();
        if (data.tablename != null)
            if (oldtablename != null) {
                dbupdate(data);
            } else {
                createmodel = data.documents.get("create_model");
                createmodel.createTable(
                        data.connection, data.documents, data.model);
            }
        else
            if (oldtablename != null) {
                removemodel = data.documents.get("remove_model");
                removemodel.removeTable(data.connection, oldtablename);
            }

        /*
         * atualiza modelos
         */
        insert = data.documents.get("insert_data_element");
        for (DocumentModelItem item : data.model.getItens()) {
            data.item = item;
            data.fieldname = item.getTableFieldName();
            data.element = item.getDataElement();
            data.reference = item.getReference();
            
            if (!oldmodel.contains(item)) {
                insert.run(data.connection, data.element);                
                if (insertModelItem(data.connection, item) == 0)
                    throw new IocasteException("error on model insert");
            } else {
                itemname = item.getName();
                data.olditem = oldmodel.getModelItem(itemname);
                data.oldfieldname = data.olditem.getTableFieldName();
                updateModelItem(data, oldmodel);
            }
        }
        
        for (DocumentModelItem olditem : oldmodel.getItens()) {
            if (data.model.contains(olditem))
                continue;

            if (removeModelItem(data.connection, olditem) == 0)
                throw new IocasteException("error on remove model item");
        }

        namespace = data.model.getNamespace();
        element = (namespace != null)? namespace.getDataElement() : null;
        update(data.connection, QUERIES[UPDATE_MODEL_HEAD],
                data.tablename,
                (namespace != null)? namespace.getTableFieldName() : null,
                (element != null)? element.getType() : null,
                (element != null)? element.getLength() : null,
                name);
        queries = data.documents.parseQueries(data.model);
        data.documents.cache.queries.put(name, queries);
        data.documents.cache.models.remove(name);
        data.documents.cache.models.put(name, data.model);
        
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
    private final void updateModelItem(UpdateData data, DocumentModel model)
            throws Exception {
        String shname;
        Object[] criteria;
        
        update(data.connection,
                QUERIES[DEL_SH_REF],
                getComposedName(data.olditem));
        
        /*
         * atualização do modelo
         */
        criteria = new Object[5];
        criteria[0] = data.element.getDecimals();
        criteria[1] = data.element.getLength();
        criteria[2] = data.element.getType();
        criteria[3] = data.element.isUpcase();
        criteria[4] = data.element.getName();

        if (update(data.connection, QUERIES[UPDATE_ELEMENT], criteria) < 0)
            throw new IocasteException(
                    new StringBuilder("error updating data element ").
                            append(criteria[4]).toString());
        
        criteria = new Object[7];
        criteria[0] = data.model.getName();
        criteria[1] = data.item.getIndex();
        criteria[2] = data.item.getTableFieldName();
        criteria[3] = data.element.getName();
        criteria[4] = data.item.getAttributeName();
        criteria[5] = (data.reference == null)?
                null : getComposedName(data.reference);
        criteria[6] = getComposedName(data.item);
        
        if (update(data.connection, QUERIES[UPDATE_ITEM], criteria) < 0)
            throw new IocasteException(
                    new StringBuilder("error updating model item").
                            append(criteria[6]).toString());
        
        shname = data.item.getSearchHelp();
        if (Documents.isInitial(shname))
            return;
        
        update(data.connection, QUERIES[INS_SH_REF], criteria[6], shname);
    }
    
    private final void updateTable(UpdateData data) throws Exception {
        DocumentModelItem oldreference;
        data.table.update(
                data.fieldname,
                data.element.getType(),
                data.element.getLength(),
                data.element.getDecimals());
        
        if (data.reference != null) {
            addTableColumnReference(data, data.reference);
            return;
        }
        
        oldreference = data.olditem.getReference();
        if (oldreference == null)
            return;
        
        removeTableColumnReference(data, oldreference);
    }
}

class UpdateData {
    public Documents documents;
    public DocumentModel model;
    public String fieldname, oldfieldname, tablename, dbtype;
    public DocumentModelItem olditem, reference, item;
    public DataElement element;
    public Connection connection;
    public GetDocumentModel getmodel;
    public Table table;
    public AlterTable altertable;
}