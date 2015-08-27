package org.iocaste.kernel.documents;

import java.sql.Connection;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.kernel.common.DBNames;
import org.iocaste.kernel.common.Table;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class CreateModel extends AbstractDocumentsHandler {
    
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
        String[] fkc, rfc;
        int size, dec;
        byte dbtype;
        Table table;
        String tname, refname, modelname, constraint;
        String nsfield = null;
        DocumentModelItem[] itens = model.getItens();
        
        getmodel = documents.get("get_document_model");
        size = itens.length - 1;
        
        modelname = model.getTableName();
        switch (getSystemParameter(documents, "dbtype")) {
        case "postgres":
            dbtype = DBNames.POSTGRES;
            break;
        default:
            dbtype = DBNames.MYSQL;
            break;
        }
        table = new Table(modelname, dbtype);

        namespace = model.getNamespace();
        if (namespace != null) {
            dataelement = namespace.getDataElement();
            size = dataelement.getLength();
            dec = dataelement.getDecimals();
            nsfield = namespace.getTableFieldName();
            table.key(nsfield, dataelement.getType(), size, dec);
        }
        
        for (DocumentModelItem item : itens) {
            tname = item.getTableFieldName();
            if (tname == null)
                throw new IocasteException(new StringBuilder(model.getName()).
                        append(": Table field name is null.").toString());

            dataelement = item.getDataElement();
            size = dataelement.getLength();
            dec = dataelement.getDecimals();
            if (model.isKey(item)) {
                table.key(tname, dataelement.getType(), size, dec);
                continue;
            }
            
            table.add(tname, dataelement.getType(), size, dec);
            
            reference = item.getReference();
            if (reference == null)
                continue;
            
            refmodel = null;
            if (reference.isDummy()) {
                refname = reference.getDocumentModel().getName();
                refmodel = getmodel.run(connection, documents, refname);
                refname = getComposedName(reference);
                reference = refmodel.getModelItem(reference.getName());
                if (reference == null)
                    throw new IocasteException(new StringBuilder(refname).
                            append(": is an invalid reference.").
                            toString());
            } else {
                refmodel = reference.getDocumentModel();
            }
            
            if (nsfield != null)
                fkc = new String[] {nsfield, tname};
            else
                fkc = new String[] {tname};
            
            namespace = refmodel.getNamespace();
            if (namespace != null)
                rfc = new String[] {
                    namespace.getTableFieldName(),
                    reference.getTableFieldName()};
            else
                rfc = new String[] {reference.getTableFieldName()};
            
            constraint = new StringBuilder(modelname).append("_").
                    append(tname).append("_").
                    append(refmodel.getTableName()).toString();
            
            table.constraint(constraint, refmodel.getTableName(), fkc, rfc);
        }
        
        return update(connection, table.toString());
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
        Object[] fields;
        String ns;
        String pkgnm = model.getPackage();
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
        
        fields = new Object[] {
                name,
                tablename,
                model.getClassName(),
                ns,
                nstyp,
                nslen,
                pkgnm
        };
        
        if (update(connection, QUERIES[INS_HEADER], fields) == 0)
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
