package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;

public class Model {
    
    /**
     * 
     * @param iocaste
     * @param item
     * @throws Exception
     */
    private static final void addDBColumn(Iocaste iocaste,
            DocumentModelItem item) throws Exception {
        DocumentModelItem reference;
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
        
        sb.append(Integer.toString(ddelement.getLength()));
        
        reference = item.getReference();
        if (reference != null) {
            sb.append(") foreign key references ").append(reference.
                    getDocumentModel().getTableName()).append("(").
                    append(reference.getTableFieldName());
        }
        
        query = sb.append(")").toString();
        
        iocaste.update(query);
    }
    
    /**
     * 
     * @param model
     * @param function
     * @param queries
     * @throws Exception
     */
    public static final void create(DocumentModel model, Function function,
            Map<String, Map<String, String>> queries) throws Exception {
        Iocaste iocaste = new Iocaste(function);
        
        saveDocumentHeader(iocaste, model);
        saveDocumentItens(iocaste, model);
        saveDocumentKeys(iocaste, model);
        saveDataElements(iocaste, model);
        
        Common.parseQueries(model, queries);
    }
    
    /**
     * 
     * @param documentname
     * @param function
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final DocumentModel get(String documentname,
            Function function, Map<String, Map<String, String>> queries)
                    throws Exception {
        Object[] lines;
        String modelref, itemref;
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
            item.setDataElement(DataElementServices.get(iocaste,
                    (String)columns.get("ENAME")));
            item.setIndex(((BigDecimal)columns.get("INDEX")).intValue());
            
            modelref = (String)columns.get("MDREF");
            itemref = (String)columns.get("ITREF");
            
            if (modelref != null && itemref != null)
                item.setReference(get(modelref, function, queries).
                        getModelItem(itemref));
            
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
            Common.parseQueries(document, queries);
        
        document.setQueries(queries.get(documentname));
        
        return document;
    }
    
    /**
     * 
     * @param modelname
     * @param function
     * @return
     * @throws Exception
     */
    public static final boolean has(String modelname, Function function)
            throws Exception {
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
     */
    private static final void insertModelItem(Iocaste iocaste,
            DocumentModelItem item) throws Exception {
        DocumentModelItem reference;
        DataElement dataelement;
        String tname;
        DocumentModel model = item.getDocumentModel();
        String query = "insert into docs002 (iname, docid, index, " +
                "fname, ename, attrb, mdref, itref) values " +
                "(?, ?, ?, ?, ?, ?, ?, ?)";
        
        dataelement = item.getDataElement();
        
        tname = new StringBuilder(model.getName()).append(".").
                append(item.getName()).toString();
        
        reference = item.getReference();
        iocaste.update(query, new Object[] {tname,
                model.getName(),
                item.getIndex(),
                item.getTableFieldName(),
                dataelement.getName(),
                item.getAttributeName(),
                (reference == null)? null : reference.getDocumentModel().
                        getName(),
                (reference == null)? null : reference.getName()});
    }
    
    /**
     * 
     * @param model
     * @param function
     * @param queries
     * @throws Exception
     */
    public static final void remove(DocumentModel model, Function function,
            Map<String, Map<String, String>> queries) throws Exception {
        Iocaste iocaste = new Iocaste(function);
        String name, query = "delete from docs004 where iname = ?";
        
        for (DocumentModelKey key : model.getKeys()) {
            name = new StringBuilder(key.getModel().getName()).append(".").
                    append(key.getModelItemName()).toString();
            iocaste.update(query, new Object[] {name});
        }
        
        for (DocumentModelItem item : model.getItens())
            removeModelItem(iocaste, item);
        
        query = "delete from docs005 where tname = ?";
        
        iocaste.update(query, model.getTableName());
        
        name = model.getName();
        query = "delete from docs001 where docid = ?";
        iocaste.update(query, name);
        
        query = new StringBuilder("drop table ").append(model.getTableName()).
                toString();
        
        iocaste.update(query);
        
        queries.remove(name);
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @throws Exception
     */
    private static final void removeModelItem(Iocaste iocaste,
            DocumentModelItem item) throws Exception {
        String query = "delete from docs002 where iname = ?";
        String name = new StringBuilder(item.getDocumentModel().getName()).
                append(".").append(item.getName()).toString();
        iocaste.update(query, new Object[] {name});
    }
    
    /**
     * 
     * @param oldname
     * @param newname
     * @param function
     * @param queries
     * @throws Exception
     */
    public static final void rename(String oldname, String newname,
            Function function, Map<String, Map<String, String>> queries)
                    throws Exception {
        DocumentModel model = Model.get(oldname, function, queries);
        
        model.setName(newname);
        Model.create(model, function, queries);
        
        model.setName(oldname);
        remove(model, function, queries);
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @throws Exception
     */
    private static final void saveDataElements(Iocaste iocaste,
            DocumentModel model) throws Exception {
        DocumentModelItem[] itens = model.getItens();
        
        for (DocumentModelItem item : itens)
            DataElementServices.insert(iocaste, item);
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @throws Exception
     */
    private static final void saveDocumentHeader(Iocaste iocaste,
            DocumentModel model) throws Exception {
        String query = "insert into docs001 (docid, tname, class) " +
                "values (?, ?, ?)";
        
        iocaste.update(query, new Object[] {
                model.getName(),
                model.getTableName(),
                model.getClassName()
        });
        
        query = "insert into docs005(tname, docid) values(? , ?)";
        
        iocaste.update(query, new Object[] {
                model.getTableName(),
                model.getName()
        });
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @throws Exception
     */
    private static final void saveDocumentItens(Iocaste iocaste,
            DocumentModel model) throws Exception {
        DataElement dataelement;
        DocumentModelItem reference;
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
            
            sb.append(dataelement.getLength());
            
            reference = item.getReference();
            if (reference != null) {
                sb.append(") foreign key references ").
                        append(model.getTableName()).
                        append("(").
                        append(reference.getDocumentModel().getTableName()).
                        append(reference.getTableFieldName());
            }
            
            sb.append((item.getIndex() == size)? ")" : "),");
        }
        
        if (sbk != null)
            sb.append(sbk).append(")");
        
        query = sb.append(")").toString();
        
        iocaste.update(query);
    }

    /**
     * 
     * @param iocaste
     * @param model
     * @throws Exception
     */
    private static void saveDocumentKeys(Iocaste iocaste, DocumentModel model)
            throws Exception {
        String dbuser, name, query = "insert into docs004(iname, docid) " +
                "values (?, ?)";
        
        for (DocumentModelKey key : model.getKeys()) {
            name = new StringBuilder(model.getName()).
                    append(".").
                    append(key.getModelItemName()).toString();
            
            iocaste.update(query, new Object[] {name,
                    key.getModel().getName()});
        }
        
        dbuser = iocaste.getSystemParameter("db.user");
        query = new StringBuilder("grant select, insert, update, delete on ").
                append(model.getTableName()).
                append(" to ").
                append(dbuser).toString();
        
        iocaste.update(query);
    }
    
    /**
     * 
     * @param model
     * @param function
     * @param queries
     * @throws Exception
     */
    public static final void update(DocumentModel model, Function function,
            Map<String, Map<String, String>> queries) throws Exception {
        DocumentModel oldmodel = get(model.getName(), function, queries);
        Iocaste iocaste = new Iocaste(function);
        
        for (DocumentModelItem item : model.getItens()) {
            if (!oldmodel.contains(item)) {
                DataElementServices.insert(iocaste, item);
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
        
        Common.parseQueries(model, queries);
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @throws Exception
     */
    private static final void removeDBColumn(Iocaste iocaste,
            DocumentModelItem item) throws Exception {
        String fieldname = item.getTableFieldName();
        String tablename = item.getDocumentModel().getTableName();
        String query = new StringBuilder("alter table ").append(tablename).
                append(" drop column ").append(fieldname).toString();
        
        iocaste.update(query);
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @param oldmodel
     * @throws Exception
     */
    private static final void updateModelItem(Iocaste iocaste,
            DocumentModelItem item, DocumentModel oldmodel) throws Exception {
        StringBuilder sb;
        DataElement ddelement;
        Object[] criteria;
        DocumentModel model = item.getDocumentModel();
        DocumentModelItem reference, olditem = oldmodel.
                getModelItem(item.getName());
        
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
            
            iocaste.update(sb.toString());
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
        
        iocaste.update(query);
        
        reference = item.getReference();
        if (reference != null) {
            if (olditem.getReference() == null) {
                query = new StringBuilder("alter table ").
                        append(tablename).
                        append(" add foreign key (").
                        append(item.getTableFieldName()).
                        append(") references ").
                        append(reference.getDocumentModel().getTableName()).
                        append("(").
                        append(reference.getTableFieldName()).
                        append(")").toString();
                
                iocaste.update(query);
            }
        } else {
            if (olditem.getReference() != null) {
                query = new StringBuilder("alter table").
                        append(tablename).
                        append(" drop constraint ").
                        append(item.getTableFieldName()).toString();
                
                iocaste.update(query);
            }
        }
        
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
                "ename = ?, attrb = ?, mdref = ?, itref = ? where iname = ?";
        
        criteria = new Object[8];
        
        criteria[0] = model.getName();
        criteria[1] = item.getIndex();
        criteria[2] = item.getTableFieldName();
        criteria[3] = ddelement.getName();
        criteria[4] = item.getAttributeName();
        criteria[5] = (reference == null)? null : reference.getDocumentModel().
                getName();
        criteria[6] = (reference == null)? null : reference.getName();
        criteria[7] = new StringBuilder(model.getName()).
                append(".").
                append(item.getName()).toString();
        
        iocaste.update(query, criteria);
    }
    
    /**
     * 
     * @param model
     * @param function
     * @param queries
     * @return
     * @throws Exception
     */
    public static final int validate(DocumentModel model, Function function,
            Map<String, Map<String, String>> queries) throws Exception {
        DocumentModel tablemodel = Model.get("TABLE_MODEL", function, queries);
        ExtendedObject link = Query.get(tablemodel, model.getTableName(),
                function);
        
        if (link != null)
            return Documents.TABLE_ALREADY_ASSIGNED;
        
        return 0;
    }

}
