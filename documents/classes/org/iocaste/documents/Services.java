package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    private Map<String, Map<String, String>> queries;
    
    public Services() {
        queries = new HashMap<String, Map<String, String>>();
        export("get_next_number", "getNextNumber");
        export("get_document_model", "getDocumentModel");
    }
    
    /**
     * 
     * @param iocaste
     * @param name
     * @param elements
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private final DataElement getDataElement(Iocaste iocaste, String name,
            Map<String, DataElement> elements) throws Exception {
        Object[] lines;
        DataElement element;
        Map<String, Object> columns;
        
        if (elements.containsKey(name))
            return elements.get(name);
        
        lines = iocaste.select("select * from docs003 where ename = ?",
                new Object[] {name});
        
        if (lines.length == 0)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        element = new DataElement();
        element.setName(name);
        element.setType(((BigDecimal)columns.get("ETYPE")).intValue());
        element.setLength(((BigDecimal)columns.get("LNGTH")).intValue());
        element.setDecimals(((BigDecimal)columns.get("DECIM")).intValue());
        elements.put(name, element);
        
        return element;
    }
    
    /**
     * Retorna modelo de documento especificado.
     * @param mensagem
     * @return modelo de documento
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public final DocumentModel getDocumentModel(Message message)
            throws Exception {
        Object[] lines;
        Map<String, Object> columns;
        DocumentModelKey key;
        DocumentModelItem item;
        DocumentModel document = null;
        String documentname = message.getString("name");
        Iocaste iocaste = new Iocaste(this);
        Map<String, DataElement> elements = new HashMap<String, DataElement>();
        
        if (documentname == null)
            throw new Exception("Document model not specified.");

        lines = iocaste.select("select * from docs001 where docid = ?",
                new Object[] {documentname});
        if (lines.length == 0)
            throw new Exception("Document not found.");
        
        columns = (Map<String, Object>)lines[0];
        document = new DocumentModel();
        
        document.setName((String)columns.get("DOCID"));
        document.setTableName((String)columns.get("TNAME"));
        document.setClassName((String)columns.get("CLASS"));
        
        lines = iocaste.select("select * from docs002 where docid = ?",
                new Object[] {documentname});
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            
            item = new DocumentModelItem();
            item.setDocumentModel(document);
            item.setName((String)columns.get("INAME"));
            item.setAttributeName((String)columns.get("ATTRB"));
            item.setTableFieldName((String)columns.get("FNAME"));
            item.setDataElement(getDataElement(iocaste,
                    (String)columns.get("ENAME"), elements));
            
            document.add(item);
        }
        
        lines = iocaste.select("select * from docs004 where docid = ?",
                new Object[] {documentname});
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            
            key = new DocumentModelKey();
            key.setModel(document);
            key.setModelItem((String)columns.get("INAME"));
            
            document.addKey(key);
        }
        
        if (!queries.containsKey(documentname))
            parseQueries(document);
        
        document.setQueries(queries.get(documentname));
        
        return document;
    }
    
    /**
     * Retorna próximo número do range
     * @param messagem
     * @return próximo número
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public final long getNextNumber(Message message) throws Exception {
        long current;
        Object[] lines;
        Map<String, Object> columns;
        String ident = message.getString("range");
        Iocaste iocaste = new Iocaste(this);
        
        if (ident == null)
            throw new Exception("Numeric range not specified.");

        lines = iocaste.select("select crrnt from range001 where ident = ?",
                new Object[] {ident});
        
        if (lines.length == 0)
            throw new Exception("Range \""+ident+"\" not found.");
        
        columns = (Map<String, Object>)lines[0];
        current = ((Long)columns.get("CRRNT"))+1;
        iocaste.update("update range001 set crrnt = ? where ident = ?",
                new Object[] {current, ident});
        iocaste.commit();
        
        return current;
    }
    
    /**
     * 
     * @param model
     */
    private void parseQueries(DocumentModel model) {
        String fieldname;
        boolean iskey;
        boolean setok = false;
        int k = 0;
        String tablename = model.getTableName();
        StringBuilder update = new StringBuilder("update ").
                append(tablename).append(" set ");
        StringBuilder insert = new StringBuilder("insert into ").
                append(tablename).append(" (");
        StringBuilder values = new StringBuilder(") values (");
        StringBuilder where = new StringBuilder(" where ");
        List<Object> criteria = new ArrayList<Object>();
        List<Object> into = new ArrayList<Object>();
        Map<String, String> queries = new HashMap<String, String>();
        
        for (DocumentModelItem modelitem : model.getItens()) {
            iskey = model.isKey(modelitem);
            
            if (k++ > 0) {
                insert.append(", ");
                values.append(", ");
                if (iskey) {
                    where.append(" and ");
                    setok = false;
                } else {
                    if (setok)
                        update.append(", ");
                    
                    setok = true;
                }
            }
            
            fieldname = modelitem.getTableFieldName();
            insert.append(fieldname);
            
            values.append("?");
            if (iskey) {
                where.append(fieldname).append("=?");
            } else {
                update.append(fieldname).append("=?");
            }
            
            into.add("?");
            if (iskey)
                criteria.add("?");
        }

        update.append(where);
        insert.append(values).append(")");
        
        queries.put("insert", insert.toString());
        queries.put("update", update.toString());
        
        this.queries.put(model.getName(), queries);
    }
}
