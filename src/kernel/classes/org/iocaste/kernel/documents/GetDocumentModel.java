package org.iocaste.kernel.documents;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.kernel.documents.dataelement.GetDataElement;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetDocumentModel extends AbstractDocumentsHandler {

    @SuppressWarnings("unchecked")
    public final DocumentModel build(Connection connection, Documents documents,
            String modelname, boolean broken) throws Exception {
        DataElement element;
        DocumentModel document, reference;
        int i;
        String itemref, name, namespace;
        String[] composed;
        Object[] shlines, lines;
        Map<String, Object> columns;
        DocumentModelItem item;
        GetDataElement getde;
        
        lines = select(connection, QUERIES[DOCUMENT], 1, modelname);
        if (lines == null)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        document = new DocumentModel((String)columns.get("DOCID"));
        document.setTableName((String)columns.get("TNAME"));
        document.setClassName((String)columns.get("CLASS"));
        document.setPackage((String)columns.get("PKGNM"));
        namespace = (String)columns.get("NSCOL");
        if (namespace != null) {
            element = new DataElement("namespace");
            element.setType(((BigDecimal)columns.get("NSTYP")).intValue());
            element.setLength(((BigDecimal)columns.get("NSLEN")).intValue());
            
            item = new DocumentModelItem("namespace");
            item.setTableFieldName(namespace);
            item.setDataElement(element);
            document.setNamespace(item);
        }
        
        getde = documents.get("get_data_element");
        lines = select(connection, QUERIES[DOC_ITEM], 0, modelname);
        if (lines != null)
            for (Object object : lines) {
                columns = (Map<String, Object>)object;
                name = (String)columns.get("INAME");
                composed = name.split("\\.");
                
                item = new DocumentModelItem(composed[1]);
                item.setDocumentModel(document);
                item.setAttributeName((String)columns.get("ATTRB"));
                item.setTableFieldName((String)columns.get("FNAME"));
                item.setDataElement(
                        getde.run(connection, (String)columns.get("ENAME")));
                item.setIndex(((BigDecimal)columns.get("NRITM")).intValue());
                
                itemref = (String)columns.get("ITREF");
                if (itemref != null) {
                    composed = itemref.split("\\.");
                    reference = run(connection, documents, composed[0], broken);
                    try {
                        if (reference != null)
                            item.setReference(
                                    reference.getModelItem(composed[1]));
                    } catch (Exception e) {
                        if (!broken)
                            throw e;
                        /*
                         * podemos recuperar o modelo à todo custo.
                         * mas avisamos que está corrompido.
                         */
                        document.corrupted();
                    }
                }
                
                shlines = select(connection, QUERIES[SH_REFERENCE], 0, name);
                if (shlines != null) {
                    columns = (Map<String, Object>)shlines[0];
                    item.setSearchHelp((String)columns.get("SHCAB"));
                }
                
                i = item.getIndex();
                document.add(item);
                item.setIndex(i);
            }
        
        lines = select(connection, QUERIES[TABLE_INDEX], 0, modelname);
        if (lines != null)
            for (Object object : lines) {
                columns = (Map<String, Object>)object;
                composed = ((String)columns.get("INAME")).split("\\.");
                document.add(new DocumentModelKey(composed[1]));
            }
        
        return document;
    }
    @Override
    public Object run(Message message) throws Exception {
        String modelname = message.getst("name");
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        return run(connection, documents, modelname, false);
    }

    public DocumentModel run(Connection connection, Documents documents,
            String modelname) throws Exception {
        return run(connection, documents, modelname, false);
    }
    
    public DocumentModel run(Connection connection, Documents documents,
            String modelname, boolean broken) throws Exception {
        Map<String, String> queries;
        DocumentModel document;
        
        if (modelname == null)
            throw new IocasteException("Document model not specified.");
        
        if (documents.cache.models.containsKey(modelname))
            return documents.cache.models.get(modelname);
        
        document = build(connection, documents, modelname, broken);
        if (document == null)
            return null;
        
        queries = documents.parseQueries(document);
        if (queries == null)
            return document;
        
        documents.cache.queries.put(modelname, queries);
        documents.cache.models.put(modelname, document);
        
        return document;
    }

}
