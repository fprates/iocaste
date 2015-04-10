package org.iocaste.kernel.documents;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetDocumentModel extends AbstractDocumentsHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String modelname = message.getString("name");
        Documents documents = getFunction();
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        return run(connection, documents, modelname);
    }
    
    @SuppressWarnings("unchecked")
    public DocumentModel run(Connection connection, Documents documents,
            String modelname) throws Exception {
        int i;
        Object[] lines, shlines;
        String itemref, name;
        String[] composed;
        Map<String, Object> columns;
        DocumentModelItem item;
        DocumentModel document;
        GetDataElement getde;
        
        if (modelname == null)
            throw new IocasteException("Document model not specified.");
        
        if (documents.cache.models.containsKey(modelname))
            return documents.cache.models.get(modelname);
        
        lines = select(connection, QUERIES[DOCUMENT], 1, modelname);
        if (lines == null)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        document = new DocumentModel((String)columns.get("DOCID"));
        document.setTableName((String)columns.get("TNAME"));
        document.setClassName((String)columns.get("CLASS"));
        
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
                    item.setReference(run(connection, documents, composed[0]).
                            getModelItem(composed[1]));
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
        
        if (!documents.cache.queries.containsKey(modelname))
            documents.parseQueries(document);
        
        document.setQueries(documents.cache.queries.get(modelname));
        documents.cache.models.put(modelname, document);
        return document;
    }

}
