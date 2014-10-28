package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class CreateModel extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        Documents documents;
        String name;
        DocumentModel model;
        Connection connection;
        
        model = message.get("model");
        documents = getFunction();
        connection = documents.database.getDBConnection(message.getSessionid());
        registerModel(connection, documents, model);
        if (model.getTableName() != null)
            createTable(connection, documents, model);
        
        name = model.getName();
        model.setQueries(documents.cache.queries.get(name));
        documents.cache.models.put(name, model);
        
        return 1;
    }
    
    /**
     * 
     * @param model
     * @param cache
     * @return
     * @throws Exception
     */
    private final int createTable(Connection connection, Documents documents,
            DocumentModel model) throws Exception {
        GetDataElement getde;
        DocumentModel refmodel;
        DataElement dataelement;
        DocumentModelItem reference;
        String tname, query, refname, dbtype;
        GetDocumentModel getmodel;
        int size;
        StringBuilder sb, sbk = null;
        DocumentModelItem[] itens = model.getItens();
        String refstmt;

        getmodel = documents.get("get_document_model");
        dbtype = getSystemParameter(documents, "dbtype");
        size = itens.length - 1;
        refstmt = getReferenceStatement(documents);
        sb = new StringBuilder("create table ").append(model.getTableName()).
                append("(");
        
        getde = documents.get("get_data_element");
        for (DocumentModelItem item : itens) {
            tname = item.getTableFieldName();
            if (tname == null)
                throw new IocasteException("Table field name is null.");
            
            sb.append(tname);
            dataelement = item.getDataElement();
            if (dataelement.isDummy()) {
                dataelement = getde.run(connection, dataelement.getName());
                item.setDataElement(dataelement);
            }

            setTableFieldsString(sb, dataelement, dbtype);
            if (model.isKey(item)) {
                if (sbk == null)
                    sbk = new StringBuilder(", primary key(");
                else
                    sbk.append(", ");
                
                sbk.append(tname);
            }
            
            reference = item.getReference();
            if (reference != null) {
                if (reference.isDummy()) {
                    refname = reference.getDocumentModel().getName();
                    refmodel = getmodel.run(connection, documents, refname);
                    refname = getComposedName(reference);
                    reference = refmodel.getModelItem(reference.getName());
                    if (reference == null)
                        throw new IocasteException(new StringBuilder(refname).
                                append(": is an invalid reference.").
                                toString());
                }
                
                sb.append(refstmt).append(reference.getDocumentModel().
                        getTableName()).append("(").
                        append(reference.getTableFieldName()).append(")");
            }
            
            if (size != item.getIndex())
                sb.append(", ");
        }
        
        if (sbk != null)
            sb.append(sbk).append(")");

        query = sb.append(")").toString();
        return update(connection, query);
    }
    
    /**
     * 
     * @param model
     * @return
     * @throws Exception
     */
    private final int registerDataElements(Connection connection,
            Documents documents, DocumentModel model) throws Exception {
        CreateDataElement createde;
        String name;
        DataElement element;
        DocumentModelItem[] itens = model.getItens();
        
        createde = documents.get("create_data_element");
        for (DocumentModelItem item : itens) {
            element = item.getDataElement();
            if (element == null)
                throw new IocasteException(new StringBuilder(item.getName()).
                        append(" has null data element.").toString());
            
            switch (element.getType()) {
            case DataType.DATE:
                element.setLength(10);
                element.setDecimals(0);
                element.setUpcase(false);
                
                break;
            case DataType.TIME:
                element.setLength(8);
                element.setDecimals(0);
                element.setUpcase(false);
                
                break;
            case DataType.BOOLEAN:
                element.setLength(1);
                element.setDecimals(0);
                element.setUpcase(false);
                
                break;
            }
            
            name = element.getName();
            if (select(connection, QUERIES[ELEMENT], 1, name) != null)
                continue;
            
            if (element.isDummy())
                throw new IocasteException(new StringBuilder(name).
                        append(": is an invalid data element.").toString());
            
            createde.run(connection, element);
        }
        
        return 1;
    }
    
    private final int registerDocumentHeader(Connection connection,
            Documents documents, DocumentModel model) throws Exception {
        GetDocumentModel getmodel;
        int l;
        String name = model.getName();
        String tablename = model.getTableName();
        
        getmodel = documents.get("get_document_model");
        if (documents.cache.mmodel == null)
            documents.cache.mmodel = getmodel.run(
                    connection, documents, "MODEL");
        
        if (documents.cache.mmodel != null) {
            l = documents.getModelItemLen("NAME");
            if (name.length() > l)
                throw new IocasteException(name.concat(": " +
                        "invalid modelname length on document header"));
            
            if (tablename != null) {
                l = documents.getModelItemLen("TABLE");
                if (tablename.length() > l)
                    throw new IocasteException(tablename.concat(": " +
                            "invalid tablename length on document header"));
            }
        }
        
        if (update(connection, QUERIES[INS_HEADER],
                name, tablename, model.getClassName()) == 0)
            throw new IocasteException("document header insert error");

        if ((tablename != null) && (update(
                connection, QUERIES[INS_MODEL_REF], tablename, name) == 0))
                throw new IocasteException(
                        "header's model reference insert error");
        
        return 1;
    }
    
    private final int registerDocumentItems(Connection connection,
            Documents documents, DocumentModel model) throws Exception {
        GetDataElement getde;
        DataElement dataelement;
        DocumentModelItem[] itens = model.getItens();
        
        getde = documents.get("get_data_element");
        for (DocumentModelItem item : itens) {
            insertModelItem(connection, item);
            
            dataelement = item.getDataElement();
            if (!dataelement.isDummy())
                continue;
            
            dataelement = getde.run(connection, dataelement.getName());
            item.setDataElement(dataelement);
        }
        
        return 1;
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @return
     * @throws Exception
     */
    private int registerDocumentKeys(Connection connection, DocumentModel model)
            throws Exception {
        String modelname, name;
        
        modelname = model.getName();
        for (DocumentModelKey key : model.getKeys()) {
            name = getComposedName(model.getModelItem(key.getModelItemName()));
            if (update(connection, QUERIES[INS_KEY], name, modelname) == 0)
                throw new IocasteException("error on key insert.");
        }
        
        return 1;
    }
    
    /**
     * 
     * @param model
     * @param cache
     * @throws Exception
     */
    private final void registerModel(Connection connection, Documents documents,
            DocumentModel model) throws Exception {
        registerDataElements(connection, documents, model);
        registerDocumentHeader(connection, documents, model);
        registerDocumentItems(connection, documents, model);
        registerDocumentKeys(connection, model);
        documents.parseQueries(model);
        
    }
}
