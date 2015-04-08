package org.iocaste.kernel.documents;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.NameSpace;
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
    public DocumentModel run(Connection connection,
            Documents documents, String modelname) throws Exception {
        DocumentModel model;
        int i;
        Object[] lines, shlines;
        String itemref, name;
        String[] composed;
        Map<String, Object> columns;
        NameSpace[] nss;
        DocumentModelItem item;
        DocumentModel document;
        GetDataElement getde;
        
        if (modelname == null)
            throw new IocasteException("Document model not specified.");
        
        model = documents.cache.getModel(modelname);
        if (model != null)
            return model;
        
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
        
        nss = getNS(connection, document);
        if (nss != null)
            documents.cache.nsmodels.put(modelname, nss);
        
        if (!documents.cache.queries.containsKey(modelname))
            documents.cache.queries.put(
                    modelname, documents.parseQueries(document));
        
        documents.cache.put(modelname, document);
        return document;
    }

    @SuppressWarnings("unchecked")
    private NameSpace[] getNS(Connection connection, DocumentModel model)
            throws Exception {
        Map<String, Object> values;
        int i;
        String nsname;
        Object[] result, items;
        String query, modelname;
        NameSpace[] ns;
        
        query = "select * from NSHEAD where MODEL = ?";
        modelname = model.getName();
        result = select(connection, query, 0, modelname);
        if (result == null)
            return null;
        
        i = 0;
        ns = new NameSpace[result.length];
        query = "select * from NSMODELS where NSKEY = ?";
        for (Object line : result) {
            values = (Map<String, Object>)line;
            nsname = (String)values.get("NSKEY");
            ns[i] = new NameSpace(nsname);
            ns[i].keymodel = modelname;
            ns[i].cmodels = new HashSet<>();
            
            items = select(connection, query, 0, nsname);
            if (items != null)
                for (Object item : items) {
                    values = (Map<String, Object>)item;
                    ns[i].cmodels.add((String)values.get("CMKEY"));
                }
            
            i++;
        }
        
        return ns;
    }

}
