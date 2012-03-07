package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;

public class DocumentServices {
    private Map<String, Map<String, String>> queries;
    private Function function;
    
    public DocumentServices(Function function) {
        this.function = function;
        queries = new HashMap<String, Map<String, String>>();
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @throws Exception
     */
    private final void addDBColumn(Iocaste iocaste, DocumentModelItem item) 
            throws Exception {
        String query, modelname = item.getDocumentModel().getTableName();
        StringBuilder sb = new StringBuilder("alter table ").append(modelname);
        DataElement ddelement = item.getDataElement();
        
        sb.append(" add column ").append(item.getTableFieldName());
        
        switch (ddelement.getType()) {
        case DataType.CHAR:
            sb.append(" varchar(");
            break;
        case DataType.NUMC:
            sb.append(" numeric(");
            break;
        }
        
        sb.append(Integer.toString(ddelement.getLength())).append(")");
        
        query = sb.toString();
        
        iocaste.update(query, null);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void createModel(DocumentModel model)
            throws Exception {
        Iocaste iocaste = new Iocaste(function);
        
        saveDocumentHeader(iocaste, model);
        saveDocumentItens(iocaste, model);
        saveDocumentKeys(iocaste, model);
        saveDataElements(iocaste, model);
        
        parseQueries(model);
    }
    
    /**
     * 
     * @param iocaste
     * @param object
     * @return
     * @throws Exception
     */
    public final int delete(Iocaste iocaste, ExtendedObject object)
            throws Exception {
        int i = 0;
        DocumentModel model = object.getModel();
        Set<DocumentModelKey> keys = model.getKeys();
        Object[] criteria = new Object[keys.size()];
        
        for (DocumentModelKey key : keys)
            criteria[i++] = object.getValue(model.
                    getModelItem(key.getModelItemName()));
        
        return iocaste.update(model.getQuery("delete"), criteria);
    }
    
    /**
     * 
     * @param iocaste
     * @param name
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public final DataElement getDataElement(Iocaste iocaste, String name)
            throws Exception {
        Object[] lines;
        DataElement element;
        Map<String, Object> columns;
        
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
        
        return element;
    }
    
    /**
     * Retorna modelo de documento especificado.
     * @param mensagem
     * @return modelo de documento
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public final DocumentModel getDocumentModel(String documentname)
            throws Exception {
        Object[] lines;
        String[] composed;
        Map<String, Object> columns;
        DocumentModelKey key;
        DocumentModelItem item;
        DocumentModel document = null;
        Iocaste iocaste = new Iocaste(function);
        
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
                    (String)columns.get("ENAME")));
            item.setIndex(((BigDecimal)columns.get("INDEX")).intValue());
            
            document.add(item);
        }
        
        lines = iocaste.select("select * from docs004 where docid = ?",
                new Object[] {documentname});
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            
            key = new DocumentModelKey();
            key.setModel(document);
            composed = ((String)columns.get("INAME")).split("\\.");
            key.setModelItem(composed[1]);
            
            document.addKey(key);
        }
        
        if (!queries.containsKey(documentname))
            parseQueries(document);
        
        document.setQueries(queries.get(documentname));
        
        return document;
    }
    
    /**
     * 
     * @param model
     * @param line
     * @return
     */
    private final ExtendedObject getExtendedObjectFrom(DocumentModel model,
            Map<String, Object> line) {
        Object value;
        ExtendedObject object = new ExtendedObject(model);
        
        for (DocumentModelItem modelitem : model.getItens()) {
            value = line.get(modelitem.getTableFieldName());
            object.setValue(modelitem, value);
        }
        
        return object;
    }
    
    /**
     * 
     * @param model
     * @return
     */
    private final DocumentModelItem getModelKey(DocumentModel model) {
        for (DocumentModelKey key : model.getKeys())
            return model.getModelItem(key.getModelItemName());
        
        return null;
    }
    
    /**
     * 
     * @param model
     * @param key
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public final ExtendedObject getObject(DocumentModel model, Object key)
            throws Exception {
        Iocaste iocaste = new Iocaste(function);
        String query = new StringBuilder("select * from ").
                append(model.getTableName()).
                append(" where ").
                append(getModelKey(model).getTableFieldName()).
                append(" = ?").toString();
        
        Object[] objects = iocaste.select(query, new Object[] {key});
        
        if (objects.length == 0)
            return null;
        
        return getExtendedObjectFrom(model, (Map<String, Object>)objects[0]);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean hasModel(String modelname) throws Exception {
        Object[] lines;
        Iocaste iocaste;
        
        if (modelname == null)
            throw new Exception("Document model not specified.");

        iocaste = new Iocaste(function);
        
        lines = iocaste.select("select docid from docs001 where docid = ?",
                new Object[] {modelname});
        
        return (lines.length == 0)? false : true;
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @throws Exception
     */
    private final void insertDataElement(Iocaste iocaste,
            DocumentModelItem item) throws Exception {
        DataElement dataelement;
        String name, query = "insert into docs003(ename, decim, lngth, " +
                "etype, upcas) values(?, ?, ?, ?, ?)";
        DocumentModel model = item.getDocumentModel();
        
        dataelement = item.getDataElement();
        name = new StringBuilder(model.getName()).append(".").
                append(item.getName()).toString();
        iocaste.update(query, new Object[] {name,
                dataelement.getDecimals(),
                dataelement.getLength(),
                dataelement.getType(),
                dataelement.isUpcase()});
        
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     */
    private final void insertModelItem(Iocaste iocaste, DocumentModelItem item) 
            throws Exception {
        DataElement dataelement;
        String tname;
        DocumentModel model = item.getDocumentModel();
        String query = "insert into docs002 (iname, docid, index, " +
                "fname, ename, attrb) values (?, ?, ?, ?, ?, ?)";
        
        dataelement = item.getDataElement();
        
        tname = new StringBuilder(model.getName()).append(".").
                append(item.getName()).toString();
        
        iocaste.update(query, new Object[] {tname,
                model.getName(),
                item.getIndex(),
                item.getTableFieldName(),
                dataelement.getName(),
                item.getAttributeName()});
    }
    
    /**
     * 
     * @param iocaste
     * @param object
     * @throws Exception
     */
    public final void modify(Iocaste iocaste, ExtendedObject object)
            throws Exception {
        String query;
        Object value;
        int nrregs;
        DocumentModel model = object.getModel();
        List<Object> criteria = new ArrayList<Object>();
        List<Object> uargs = new ArrayList<Object>();
        List<Object> iargs = new ArrayList<Object>();
        
        for (DocumentModelItem item : model.getItens()) {
            value = object.getValue(item);
            
            iargs.add(value);
            if (model.isKey(item))
                criteria.add(value);
            else
                uargs.add(value);
        }
        
        uargs.addAll(criteria);
        nrregs = 0;
        
        query = model.getQuery("update");
        if (query != null)
            nrregs = iocaste.update(query, uargs.toArray());
        
        if (nrregs == 0)
            iocaste.update(model.getQuery("insert"), iargs.toArray());
    }
    
    /**
     * 
     * @param model
     */
    private final void parseQueries(DocumentModel model) {
        String fieldname;
        boolean iskey, setok = false;
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

        if (setok)
            update.append(where);
        
        insert.append(values).append(")");
        delete.append(where);
        
        queries.put("insert", insert.toString());
        
        if (setok)
            queries.put("update", update.toString());
        
        queries.put("delete", delete.toString());
        
        fieldname = model.getName();
        if (queries.containsKey(fieldname))
            this.queries.remove(fieldname);
        
        this.queries.put(fieldname, queries);
    }
    
    /**
     * 
     * @param query
     * @return
     * @throws Exception
     */
    public final QueryInfo parseQuery(String query) throws Exception {
        String upcasetoken;
        String[] select, parsed = query.split(" ");
        int t, pass = 0;
        StringBuilder sb = new StringBuilder("select ");
        QueryInfo queryinfo = new QueryInfo();
        
        for (String token : parsed) {
            upcasetoken = token.toUpperCase();
            
            switch (pass) {
            case 0:
                if (upcasetoken.equals("SELECT")) {
                    pass = 1;
                    continue;
                }
                
                if (upcasetoken.equals("FROM")) {
                    pass = 3;
                    sb.append("* from ");
                    continue;
                }
                
                continue;
            case 1:
                select = token.split(",");
                t = select.length;
                
                for (int i = 0; i < t; i++) {
                    sb.append(select[i]);
                    if (i == (t - 1))
                        continue;
                    sb.append(",");
                }
                
                pass = 2;
                continue;
            case 2:
                if (upcasetoken.equals("FROM"))
                    sb.append(" from ");
                
                pass = 3;
                continue;
            case 3:
                queryinfo.model = getDocumentModel(upcasetoken);
                if (queryinfo.model == null)
                    throw new Exception("Document model not found.");
                
                sb.append(queryinfo.model.getTableName());
                pass = 4;
                continue;
            case 4:
                if (upcasetoken.equals("WHERE"))
                    sb.append(" where ");
                else
                    continue;
                
                pass = 5;
                continue;
            case 5:
                continue;
            }
        }
        
        queryinfo.query = sb.toString();
        
        return queryinfo;
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @throws Exception
     */
    private final void removeDBColumn(Iocaste iocaste, DocumentModelItem item)
            throws Exception {
        String fieldname = item.getTableFieldName();
        String tablename = item.getDocumentModel().getTableName();
        String query = new StringBuilder("alter table ").append(tablename).
                append(" drop column ").append(fieldname).toString();
        
        iocaste.update(query, null);
    }
    
    /**
     * 
     * @param model
     * @throws Exception
     */
    public final void removeModel(DocumentModel model) throws Exception {
        Iocaste iocaste = new Iocaste(function);
        String name, query = "delete from docs004 where iname = ?";
        
        for (DocumentModelKey key : model.getKeys()) {
            name = new StringBuilder(key.getModel().getName()).append(".").
                    append(key.getModelItemName()).toString();
            iocaste.update(query, new Object[] {name});
        }
        
        for (DocumentModelItem item : model.getItens())
            removeModelItem(iocaste, item);
        
        query = "delete from docs001 where docid = ?";
        iocaste.update(query, new Object[] {model.getName()});
        
        query = new StringBuilder("drop table ").append(model.getTableName()).
                toString();
        iocaste.update(query, null);
    }
    
    /**
     * 
     * @param oldname
     * @param newname
     * @throws Exception
     */
    public final void renameModel(String oldname, String newname)
            throws Exception {
        DocumentModel model = getDocumentModel(oldname);
        
        model.setName(newname);
        createModel(model);
        
        model.setName(oldname);
        removeModel(model);
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @throws Exception
     */
    private final void removeModelItem(Iocaste iocaste, DocumentModelItem item)
            throws Exception {
        String query = "delete from docs002 where iname = ?";
        String name = new StringBuilder(item.getDocumentModel().getName()).
                append(".").append(item.getName()).toString();
        iocaste.update(query, new Object[] {name});
    }
    
    /**
     * 
     * @param iocaste
     * @param object
     * @return
     * @throws Exception
     */
    public final int save(Iocaste iocaste, ExtendedObject object)
            throws Exception {
        Object[] criteria;
        DocumentModel model = object.getModel();
        DocumentModelItem[] itens = model.getItens();
        int i = itens.length;
        
        criteria = (i > 0)? new Object[i] : null;
        
        i = 0;
        for (DocumentModelItem item : model.getItens())
            criteria[i++] = object.getValue(item);
        
        return iocaste.update(model.getQuery("insert"), criteria);
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @throws Exception
     */
    private final void saveDataElements(Iocaste iocaste, DocumentModel model) 
            throws Exception {
        DocumentModelItem[] itens = model.getItens();
        
        for (DocumentModelItem item : itens)
            insertDataElement(iocaste, item);
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @throws Exception
     */
    private final void saveDocumentHeader(Iocaste iocaste,
            DocumentModel model) throws Exception {
        String query = "insert into docs001 (docid, tname, class) " +
                "values (?, ?, ?)";
        
        iocaste.update(query, new Object[] {model.getName(),
                                            model.getTableName(),
                                            model.getClassName()});
        
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @throws Exception
     */
    private final void saveDocumentItens(Iocaste iocaste,
            DocumentModel model) throws Exception {
        DataElement dataelement;
        int size;
        StringBuilder sb, sbk = null;
        String tname, query = "insert into docs002 (iname, docid, index, " +
                "fname, ename, attrb) values (?, ?, ?, ?, ?, ?)";
        DocumentModelItem[] itens = model.getItens();
        
        sb = new StringBuilder("create table ").append(model.getTableName()).
                append(" (");
        
        size = itens.length - 1;
        
        for (DocumentModelItem item : itens) {
            insertModelItem(iocaste, item);
            
            tname = item.getTableFieldName();
            sb.append(tname);
            
            dataelement = item.getDataElement();
            
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
     * @param model
     * @throws Exception
     */
    private void saveDocumentKeys(Iocaste iocaste, DocumentModel model)
            throws Exception {
        String dbuser, name, query = "insert into docs004(iname, docid) " +
                "values (?, ?)";
        
        for (DocumentModelKey key : model.getKeys()) {
            name = new StringBuilder(model.getName()).append(".").
                    append(key.getModelItemName()).toString();
            iocaste.update(query, new Object[] {name,
                    key.getModel().getName()});
        }
        
        dbuser = iocaste.getSystemParameter("db.user");
        query = new StringBuilder("grant select, insert, update, delete on ").
                append(model.getTableName()).append(" to ").append(dbuser).
                toString();
        
        iocaste.update(query, null);
    }
    
    /**
     * 
     * @param iocaste
     * @param query
     * @param criteria
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public final ExtendedObject[] select(Iocaste iocaste, String query,
            Object[] criteria) throws Exception {
        Object[] lines;
        Map<String, Object> line;
        ExtendedObject[] objects;
        QueryInfo queryinfo = parseQuery(query);
        
        if (queryinfo.query == null || queryinfo.model == null)
            return null;
        
        lines = iocaste.select(queryinfo.query, criteria);
        if (lines.length == 0)
            return null;
        
        objects = new ExtendedObject[lines.length];
        
        for (int i = 0; i < lines.length; i++) {
            line = (Map<String, Object>)lines[i];
            objects[i] = getExtendedObjectFrom(queryinfo.model, line);
        }
        
        return objects;
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @param oldmodel
     * @throws Exception
     */
    private final void updateModelItem(Iocaste iocaste,
            DocumentModelItem item, DocumentModel oldmodel) throws Exception {
        StringBuilder sb;
        DataElement ddelement;
        Object[] criteria;
        DocumentModel model = item.getDocumentModel();
        DocumentModelItem olditem = oldmodel.getModelItem(item.getName());
        
        String tablename = model.getTableName(),
                oldfieldname = olditem.getTableFieldName(),
                fieldname = item.getTableFieldName();
        
        String query = new StringBuilder("alter table ").
                append(tablename).
                append(" alter column ").toString();
        
        /*
         * renomeia campo da tabela
         */
        if (!fieldname.equals(oldfieldname)) {
            sb = new StringBuilder(query).
                    append(oldfieldname).
                    append(" rename to ").
                    append(fieldname);
            
            iocaste.update(sb.toString(), null);
        }
        
        /*
         * atualização das característica dos itens da tabela
         */
        sb = new StringBuilder(query);
        sb.append(fieldname);
        
        ddelement = item.getDataElement();
        
        switch (ddelement.getType()) {
        case DataType.CHAR:
            sb.append(" varchar(");
            
            break;
        case DataType.NUMC:
            sb.append(" numeric(");
            
            break;
        }
        
        sb.append(Integer.toString(ddelement.getLength()));
        sb.append(")");
        
        query = sb.toString();
        
        iocaste.update(query, null);
        
        /*
         * atualização do modelo
         */
        query = "update docs003 set decim = ?, lngth = ?, etype = ?, " +
                "upcas = ? where ename = ?";
        
        criteria = new Object[5];
        
        criteria[0] = ddelement.getDecimals();
        criteria[1] = ddelement.getLength();
        criteria[2] = ddelement.getType();
        criteria[3] = ddelement.isUpcase();
        criteria[4] = ddelement.getName();

        iocaste.update(query, criteria);
        
        query = "update docs002 set docid = ?, index = ?, fname = ?, " +
                "ename = ?, attrb = ? where iname = ?";
        
        criteria = new Object[6];
        
        criteria[0] = model.getName();
        criteria[1] = item.getIndex();
        criteria[2] = item.getTableFieldName();
        criteria[3] = ddelement.getName();
        criteria[4] = item.getAttributeName();
        criteria[5] = new StringBuilder(model.getName()).
                append(".").append(item.getName()).toString();
        
        iocaste.update(query, criteria);
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @throws Exception
     */
    public final void updateModel(Iocaste iocaste, DocumentModel model)
            throws Exception {
        DocumentModel oldmodel = getDocumentModel(model.getName());
        
        for (DocumentModelItem item : model.getItens()) {
            if (!oldmodel.contains(item)) {
                insertDataElement(iocaste, item);
                insertModelItem(iocaste, item);
                addDBColumn(iocaste, item);
            } else {
                updateModelItem(iocaste, item, oldmodel);
            }
        }
        
        for (DocumentModelItem olditem : oldmodel.getItens()) {
            if (model.contains(olditem))
                continue;
            
            removeModelItem(iocaste, olditem);
            removeDBColumn(iocaste, olditem);
        }
        
        parseQueries(model);
    }
}
