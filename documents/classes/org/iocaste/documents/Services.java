package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
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
        export("has_model", "hasModel");
        export("create_model", "createModel");
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void createModel(Message message) throws Exception {
        int size;
        StringBuilder sb, sbk = null;
        DataElement dataelement;
        Set<DocumentModelItem> itens;
        Iocaste iocaste = new Iocaste(this);
        DocumentModel model = (DocumentModel)message.get("model");
        String tname, query = "insert into docs001 (" +
        		"docid, tname, class) values (?, ?, ?)";
        
        iocaste.update(query, new Object[] {model.getName(),
                                            model.getTableName(),
                                            model.getClassName()});

        query = "insert into docs002 (iname, docid, index, " +
                "fname, ename, attrb) values (?, ?, ?, ?, ?, ?)";
        
        itens = model.getItens();
        
        sb = new StringBuilder("create table ").append(model.getTableName()).
                append(" (");
        
        size = itens.size() - 1;
        
        for (DocumentModelItem item : itens) {
            dataelement = item.getDataElement();
            
            tname = new StringBuilder(model.getName()).append(".").
                    append(item.getName()).toString();
            
            iocaste.update(query, new Object[] {tname,
                    model.getName(),
                    item.getIndex(),
                    item.getTableFieldName(),
                    dataelement.getName(),
                    item.getAttributeName()});
            
            tname = item.getTableFieldName();
            sb.append(tname);
            
            switch (dataelement.getType()) {
            case DataType.CHAR:
                sb.append(" varchar(");
                break;
                
            case DataType.NUMC:
                sb.append(" numeric(");
                break;
            }
            
            if (model.isKey(item)) {
                if (sbk == null)
                    sbk = new StringBuilder(", primary key (");
                else
                    sbk.append(",");
                
                sbk.append(tname);
            }
            
            sb.append(dataelement.getLength()).append(
                    (item.getIndex() == size)? ")" : "),");
        }
        
        if (sbk != null)
            sb.append(sbk).append(")");
        
        query = sb.append(")").toString();
        
        iocaste.update(query, null);
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
        element.setUpcase((Boolean)columns.get("UPCAS"));
        
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
        String[] composed;
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
            return null;
        
        columns = (Map<String, Object>)lines[0];
        document = new DocumentModel();
        
        document.setName((String)columns.get("DOCID"));
        document.setTableName((String)columns.get("TNAME"));
        document.setClassName((String)columns.get("CLASS"));
        
        lines = iocaste.select("select * from docs002 where docid = ?",
                new Object[] {documentname});
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            composed = ((String)columns.get("INAME")).split("\\.");
            item = new DocumentModelItem();
            item.setDocumentModel(document);
            item.setName(composed[1]);
            item.setAttributeName((String)columns.get("ATTRB"));
            item.setTableFieldName((String)columns.get("FNAME"));
            item.setDataElement(getDataElement(iocaste,
                    (String)columns.get("ENAME"), elements));
            item.setIndex(((BigDecimal)columns.get("INDEX")).intValue());
            
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
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean hasModel(Message message) throws Exception {
        Object[] lines;
        Iocaste iocaste;
        String modelname = message.getString("model_name");
        
        if (modelname == null)
            throw new Exception("Document model not specified.");

        iocaste = new Iocaste(this);
        
        lines = iocaste.select("select docid from docs001 where docid = ?",
                new Object[] {modelname});
        
        return (lines.length == 0)? false : true;
    }
    
    /**
     * 
     * @param model
     */
    private final void parseQueries(DocumentModel model) {
        String fieldname;
        boolean iskey;
        boolean setok = false;
        int k = 0;
        String tablename = model.getTableName();
        StringBuilder update = new StringBuilder("update ").
                append(tablename).append(" set ");
        StringBuilder insert = new StringBuilder("insert into ").
                append(tablename).append(" (");
        StringBuilder delete = new StringBuilder("delete from ").
                append(tablename);
        StringBuilder values = new StringBuilder(") values (");
        StringBuilder where = new StringBuilder(" where ");
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
            if (iskey)
                where.append(fieldname).append("=?");
            else
                update.append(fieldname).append("=?");
        }

        update.append(where);
        insert.append(values).append(")");
        delete.append(where);
        
        queries.put("insert", insert.toString());
        queries.put("update", update.toString());
        queries.put("delete", delete.toString());
        
        this.queries.put(model.getName(), queries);
    }
}
