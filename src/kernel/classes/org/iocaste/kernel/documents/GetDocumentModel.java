package org.iocaste.kernel.documents;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.kernel.database.Select;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetDocumentModel extends AbstractDocumentsHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String modelname = message.getString("name");
        String sessionid = message.getSessionid();
        return run(modelname, sessionid);
    }
    
    @SuppressWarnings("unchecked")
    public DocumentModel run(String modelname, String sessionid)
            throws Exception {
        int i;
        Object[] lines, shlines;
        String itemref, name;
        String[] composed;
        Map<String, Object> columns;
        DocumentModelItem item;
        DocumentModel document;
        Documents documents;
        Select select;
        Connection connection;
        GetDataElement getde;
        
        if (modelname == null)
            throw new IocasteException("Document model not specified.");
        
        documents = getFunction();
        if (documents.cache.models.containsKey(modelname))
            return documents.cache.models.get(modelname);
        
        connection = documents.database.getDBConnection(sessionid);
        select = documents.database.get("select");
        lines = select.run(connection, QUERIES[DOCUMENT], 1, modelname);
        if (lines == null)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        document = new DocumentModel((String)columns.get("DOCID"));
        document.setTableName((String)columns.get("TNAME"));
        document.setClassName((String)columns.get("CLASS"));
        
        getde = documents.get("get_data_element");
        lines = select.run(connection, QUERIES[DOC_ITEM], 0, modelname);
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            name = (String)columns.get("INAME");
            composed = name.split("\\.");
            
            item = new DocumentModelItem(composed[1]);
            item.setDocumentModel(document);
            item.setAttributeName((String)columns.get("ATTRB"));
            item.setTableFieldName((String)columns.get("FNAME"));
            item.setDataElement(
                    getde.run((String)columns.get("ENAME"), sessionid));
            item.setIndex(((BigDecimal)columns.get("NRITM")).intValue());
            
            itemref = (String)columns.get("ITREF");
            if (itemref != null) {
                composed = itemref.split("\\.");
                item.setReference(run(composed[0], sessionid).
                        getModelItem(composed[1]));
            }
            
            shlines = select.run(connection, QUERIES[SH_REFERENCE], 0, name);
            if (shlines != null) {
                columns = (Map<String, Object>)shlines[0];
                item.setSearchHelp((String)columns.get("SHCAB"));
            }
            
            i = item.getIndex();
            document.add(item);
            item.setIndex(i);
        }
        
        lines = select.run(connection, QUERIES[TABLE_INDEX], 0, modelname);
        if (lines != null)
            for (Object object : lines) {
                columns = (Map<String, Object>)object;
                composed = ((String)columns.get("INAME")).split("\\.");
                document.add(new DocumentModelKey(composed[1]));
            }
        
        if (!documents.cache.queries.containsKey(modelname))
            Documents.parseQueries(document, documents.cache.queries);
        
        document.setQueries(documents.cache.queries.get(modelname));
        documents.cache.models.put(modelname, document);
        return document;
    }

}
