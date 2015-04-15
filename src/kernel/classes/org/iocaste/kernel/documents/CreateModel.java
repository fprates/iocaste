package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class CreateModel extends AbstractDocumentsHandler {
    
    private final void addKey(StringBuilder sbk, DocumentModelItem item) {
        String tname = item.getTableFieldName();
        
        if (sbk.length() == 0)
            sbk.append(", primary key(");
        else
            sbk.append(", ");
        
        sbk.append(tname);
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
        GetDocumentModel getmodel;
        DocumentModel refmodel;
        DataElement dataelement;
        DocumentModelItem reference, namespace;
        String tname, query, refname, dbtype, refstmt;
        int size;
        StringBuilder sb, sbk = null;
        DocumentModelItem[] itens = model.getItens();
        
        getmodel = documents.get("get_document_model");
        dbtype = getSystemParameter(documents, "dbtype");
        size = itens.length - 1;
        refstmt = getReferenceStatement(documents);
        sb = new StringBuilder("create table ").append(model.getTableName()).
                append("(");

        namespace = model.getNamespace();
        if (namespace != null) {
            sbk = new StringBuilder();
            addKey(sbk, namespace);
            sb.append(namespace.getTableFieldName());
            setTableFieldsString(sb, namespace.getDataElement(), dbtype);
            sb.append(", ");
        }
        
        for (DocumentModelItem item : itens) {
            tname = item.getTableFieldName();
            if (tname == null)
                throw new IocasteException(new StringBuilder(model.getName()).
                        append(": Table field name is null.").toString());
            
            sb.append(tname);
            dataelement = item.getDataElement();
            setTableFieldsString(sb, dataelement, dbtype);
            if (model.isKey(item)) {
                if (sbk == null)
                    sbk = new StringBuilder();
                addKey(sbk, item);
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
    
    private final DocumentModelItem getModelItem(Connection connection,
            Documents documents, DocumentModel model, String name)
                    throws Exception {
        GetDocumentModel getmodel = documents.get("get_document_model");
        
        return getmodel.run(connection, documents, model.getName()).
                getModelItem(name);
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
        DocumentModelItem[] itens = model.getItens();
        
        createde = documents.get("create_data_element");
        for (DocumentModelItem item : itens)
            createde.run(connection, item.getDataElement());
        
        return 1;
    }
    
    private final int registerDocumentHeader(Connection connection,
            Documents documents, DocumentModel model) throws Exception {
        DataElement element;
        DocumentModelItem item;
        GetDocumentModel getmodel;
        int l, nstyp, nslen;
        String ns;
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
        
        item = model.getNamespace();
        if (item != null) {
            element = item.getDataElement();
            ns = item.getTableFieldName();
            nstyp = element.getType();
            nslen = element.getLength();
        } else {
            ns = null;
            nstyp = 0;
            nslen = 0;
        }
        
        if (update(connection, QUERIES[INS_HEADER],
                name, tablename, model.getClassName(), ns, nstyp, nslen) == 0)
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
        documents.cache.queries.put(
                model.getName(), documents.parseQueries(model));
    }

    private void prepareElements(Connection connection, Documents documents,
            DocumentModel model) throws Exception {
        DocumentModelItem reference;
        DataElement element;
        CreateDataElement createde;
        GetDataElement getde;
        
        createde = documents.get("create_data_element");
        getde = documents.get("get_data_element");
        for (DocumentModelItem item : model.getItens()) {
            element = item.getDataElement();
            if (element == null) {
                reference = item.getReference();
                if (reference == null)
                    throw new RuntimeException(
                            item.getName().concat(
                                    " has an undefined element or reference."));
                if (reference.isDummy())
                    reference = getModelItem(connection, documents,
                            reference.getDocumentModel(), reference.getName());
                element = reference.getDataElement();
                item.setDataElement(element);
            }
            
            if (element == null)
                throw new IocasteException(new StringBuilder(item.getName()).
                        append(" has null data element.").toString());
            
            if (element.isDummy()) {
                element = getde.run(connection, element.getName());
                item.setDataElement(element);
            } else {
                createde.prepare(element);
            }   
        }
    }
    
    @Override
    public Object run(Message message) throws Exception {
        int code;
        Documents documents;
        String name;
        DocumentModel model;
        Connection connection;
        
        model = message.get("model");
        documents = getFunction();
        connection = documents.database.getDBConnection(message.getSessionid());
        
        prepareElements(connection, documents, model);
        if (model.getTableName() != null) {
            code = createTable(connection, documents, model);
            if (code < 0)
                return code;
        }
        
        registerModel(connection, documents, model);        
        name = model.getName();
        documents.cache.models.put(name, model);
        
        return 1;
    }
}
