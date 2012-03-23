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
import org.iocaste.protocol.IocasteException;

public class Model {
    
    /**
     * 
     * @param iocaste
     * @param item
     * @return
     * @throws Exception
     */
    private static final int addDBColumn(Iocaste iocaste,
            DocumentModelItem item) throws Exception {
        DocumentModelItem reference;
        String query, modelname = item.getDocumentModel().getTableName();
        StringBuilder sb = new StringBuilder("alter table ").append(modelname);
        DataElement ddelement = item.getDataElement();
        
        sb.append(" add column ").append(item.getTableFieldName());
        
        switch (ddelement.getType()) {
        case DataType.CHAR:
            sb.append(" varchar(");
            sb.append(ddelement.getLength());
            sb.append(")");
            break;
        case DataType.NUMC:
            sb.append(" numeric(");
            sb.append(ddelement.getLength());
            sb.append(")");
            break;
        case DataType.DEC:
            sb.append(" decimal(");            
            sb.append(ddelement.getLength());
            sb.append(",");
            sb.append(ddelement.getDecimals());
            sb.append(")");
            break;
        case DataType.DATE:
            sb.append(" date");
            break;
        }
        
        reference = item.getReference();
        if (reference != null) {
            sb.append(" foreign key references ").append(reference.
                    getDocumentModel().getTableName()).append("(").
                    append(reference.getTableFieldName());
        }
        
        query = sb.append(")").toString();
        return iocaste.update(query);
    }
    
    /**
     * 
     * @param model
     * @param function
     * @param queries
     * @return
     * @throws Exception
     */
    public static final int create(DocumentModel model, Function function,
            Map<String, Map<String, String>> queries) throws Exception {
        Iocaste iocaste = new Iocaste(function);
        
        saveDocumentHeader(iocaste, model);
        saveDocumentItens(iocaste, model);
        saveDocumentKeys(iocaste, model);
        saveDataElements(iocaste, model);
        
        Common.parseQueries(model, queries);
        
        return 1;
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
        Object[] lines, shlines;
        String itemref, query, name;
        String[] composed;
        Map<String, Object> columns;
        DocumentModelItem item;
        DocumentModel document = null;
        Iocaste iocaste = new Iocaste(function);
        
        if (documentname == null)
            throw new Exception("Document model not specified.");

        query = "select * from docs001 where docid = ?";
        lines = iocaste.select(query, documentname);
        if (lines.length == 0)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        document = new DocumentModel();
        
        document.setName((String)columns.get("DOCID"));
        document.setTableName((String)columns.get("TNAME"));
        document.setClassName((String)columns.get("CLASS"));
        
        query = "select * from docs002 where docid = ?";
        lines = iocaste.select(query, documentname);
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            name = (String)columns.get("INAME");
            composed = name.split("\\.");
            
            item = new DocumentModelItem();
            item.setDocumentModel(document);
            item.setName(composed[1]);
            item.setAttributeName((String)columns.get("ATTRB"));
            item.setTableFieldName((String)columns.get("FNAME"));
            item.setDataElement(DataElementServices.get(iocaste,
                    (String)columns.get("ENAME")));
            item.setIndex(((BigDecimal)columns.get("INDEX")).intValue());
            
            itemref = (String)columns.get("ITREF");
            
            if (itemref != null) {
                composed = itemref.split("\\.");
                item.setReference(get(composed[0], function, queries).
                        getModelItem(composed[1]));
            }
            
            query = "select * from shref where iname = ?";
            shlines = iocaste.select(query, name);
            if (shlines != null && shlines.length > 0) {
                columns = (Map<String, Object>)shlines[0];
                item.setSearchHelp((String)columns.get("SHCAB"));
            }
            
            document.add(item);
        }
        
        lines = iocaste.select("select * from docs004 where docid = ?",
                documentname);
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            composed = ((String)columns.get("INAME")).split("\\.");
            
            document.add(new DocumentModelKey(composed[1]));
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
        String query;
        
        if (modelname == null)
            throw new Exception("Document model not specified.");

        query = "select docid from docs001 where docid = ?";
        lines = new Iocaste(function).select(query, modelname);
        
        return (lines.length == 0)? false : true;
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @return
     * @throws Exception
     */
    private static final int insertModelItem(Iocaste iocaste,
            DocumentModelItem item) throws Exception {
        DocumentModelItem reference;
        DataElement dataelement;
        String itemref, tname, shname;
        DocumentModel model = item.getDocumentModel();
        String query = "insert into docs002 (iname, docid, index, " +
                "fname, ename, attrb, itref) values(?, ?, ?, ?, ?, ?, ?)";
        
        dataelement = item.getDataElement();
        
        tname = Documents.getComposedName(item);
        reference = item.getReference();
        itemref = (reference == null)? null : Documents.getComposedName(reference);
        
        if (iocaste.update(query, tname,
                model.getName(),
                item.getIndex(),
                item.getTableFieldName(),
                dataelement.getName(),
                item.getAttributeName(),
                itemref) == 0)
            return 0;
        
        if (itemref != null) {
            query = "insert into docs006(iname, itref) values(?, ?)";
            if (iocaste.update(query, tname, itemref) == 0)
                return 0;
        }
        
        shname = item.getSearchHelp();
        if (shname == null)
            return 1;
        
        query = "select * from shcab where ident = ?";
        if (iocaste.select(query, shname).length == 0)
            return 1;
        
        query = "insert into shref(iname, shcab) values(?, ?)";
        return iocaste.update(query, tname, shname);
    }
    
    /**
     * 
     * @param model
     * @param function
     * @param queries
     * @return
     * @throws Exception
     */
    public static final int remove(DocumentModel model, Function function,
            Map<String, Map<String, String>> queries) throws Exception {
        Iocaste iocaste = new Iocaste(function);
        String tablename, name, query = "delete from docs004 where iname = ?";
        
        for (DocumentModelKey key : model.getKeys()) {
            name = Documents.getComposedName(key.getModel().
                    getModelItem(key.getModelItemName()));
            if (iocaste.update(query, name) == 0)
                throw new IocasteException("");
        }
        
        for (DocumentModelItem item : model.getItens())
            if (removeModelItem(iocaste, item) == 0)
                throw new IocasteException("");
        
        tablename = model.getTableName();
        query = "delete from docs005 where tname = ?";
        if (iocaste.update(query, tablename) == 0)
            throw new IocasteException("");
        
        name = model.getName();
        query = "delete from docs001 where docid = ?";
        if (iocaste.update(query, name) == 0)
            throw new IocasteException("");
        
        query = new StringBuilder("drop table ").append(tablename).
                toString();
        iocaste.update(query);
        
        queries.remove(name);
        
        return 1;
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @return
     * @throws Exception
     */
    private static final int removeDBColumn(Iocaste iocaste,
            DocumentModelItem item) throws Exception {
        String fieldname = item.getTableFieldName();
        String tablename = item.getDocumentModel().getTableName();
        String query = new StringBuilder("alter table ").append(tablename).
                append(" drop column ").append(fieldname).toString();
        
        return iocaste.update(query);
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @return
     * @throws Exception
     */
    private static final int removeModelItem(Iocaste iocaste,
            DocumentModelItem item) throws Exception {
        String sherror, query = "delete from docs006 where iname = ?";
        String name = Documents.getComposedName(item);
        
        iocaste.update(query, name);
        
        query = "delete from shref where iname = ?";
        iocaste.update(query, name);

        sherror = "there is search help dependence on item ";
        sherror = new StringBuilder(sherror).append(name).toString();
        
        query = "select * from shitm where mditm = ?";
        if (iocaste.select(query, name).length > 0)
            throw new IocasteException(sherror);
            
        query = "select * from shcab where exprt = ?";
        if (iocaste.select(query, name).length > 0)
            throw new IocasteException(sherror);
        
        query = "delete from docs002 where iname = ?";
        if (iocaste.update(query, name) == 0)
            throw new IocasteException("");
        
        return 1;
    }
    
    /**
     * 
     * @param oldname
     * @param newname
     * @param function
     * @param queries
     * @return
     * @throws Exception
     */
    public static final int rename(String oldname, String newname,
            Function function, Map<String, Map<String, String>> queries)
                    throws Exception {
        DocumentModel model = Model.get(oldname, function, queries);
        
        model.setName(newname);
        if (Model.create(model, function, queries) == 0)
            throw new IocasteException("");
        
        model.setName(oldname);
        if (remove(model, function, queries) == 0)
            throw new IocasteException("");
        
        return 1;
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @return
     * @throws Exception
     */
    private static final int saveDataElements(Iocaste iocaste,
            DocumentModel model) throws Exception {
        DataElement element;
        DocumentModelItem[] itens = model.getItens();
        String query = "select * from docs003 where ename = ?";
        
        for (DocumentModelItem item : itens) {
            element = item.getDataElement();
            
            if (iocaste.select(query, element.getName()).length > 0)
                continue;
            
            DataElementServices.insert(iocaste, item.getDataElement());
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
    private static final int saveDocumentHeader(Iocaste iocaste,
            DocumentModel model) throws Exception {
        String query = new StringBuilder("drop table ").
                append(model.getTableName()).toString();
        
        iocaste.update(query);
        
        query = "insert into docs001 (docid, tname, class) " +
                "values (?, ?, ?)";
        if (iocaste.update(query, model.getName(), model.getTableName(),
                model.getClassName()) == 0)
            throw new IocasteException("");
        
        query = "insert into docs005(tname, docid) values(? , ?)";
        if (iocaste.update(query, model.getTableName(), model.getName()) == 0)
            throw new IocasteException("");
        
        return 1;
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @return
     * @throws Exception
     */
    private static final int saveDocumentItens(Iocaste iocaste,
            DocumentModel model) throws Exception {
        DataElement dataelement;
        DocumentModelItem reference;
        int size;
        StringBuilder sb, sbk = null;
        String tname, query;
        DocumentModelItem[] itens = model.getItens();
        
        sb = new StringBuilder("create table ").append(model.getTableName()).
                append(" (");
        
        size = itens.length - 1;
        
        for (DocumentModelItem item : itens) {
            insertModelItem(iocaste, item);
            
            tname = item.getTableFieldName();
            if (tname == null)
                throw new IocasteException("Table field name is null.");
            
            sb.append(tname);
            
            dataelement = item.getDataElement();
            
            switch (dataelement.getType()) {
            case DataType.CHAR:
                sb.append(" varchar(");
                sb.append(dataelement.getLength());
                sb.append(")");
                break;
                
            case DataType.NUMC:
                sb.append(" numeric(");
                sb.append(dataelement.getLength());
                sb.append(")");
                break;
                
            case DataType.DEC:
                sb.append(" decimal(");
                sb.append(dataelement.getLength());
                sb.append(",");
                sb.append(dataelement.getDecimals());
                sb.append(")");
                break;
                
            case DataType.DATE:
                sb.append(" date");
            }
            
            if (model.isKey(item)) {
                if (sbk == null)
                    sbk = new StringBuilder(", primary key (");
                else
                    sbk.append(",");
                
                sbk.append(tname);
            }
            
            reference = item.getReference();
            if (reference != null) {
                sb.append(" foreign key references ").
                        append(reference.getDocumentModel().getTableName()).
                        append("(").
                        append(reference.getTableFieldName());
            }
            
            if (size != item.getIndex())
                sb.append(",");
        }
        
        if (sbk != null)
            sb.append(sbk).append(")");
        
        query = sb.append(")").toString();
        return iocaste.update(query);
    }

    /**
     * 
     * @param iocaste
     * @param model
     * @return
     * @throws Exception
     */
    private static int saveDocumentKeys(Iocaste iocaste, DocumentModel model)
            throws Exception {
        String dbuser, name, query = "insert into docs004(iname, docid) " +
                "values (?, ?)";
        
        for (DocumentModelKey key : model.getKeys()) {
            name = Documents.getComposedName(model.
                    getModelItem(key.getModelItemName()));
            
            if (iocaste.update(query, name, key.getModel().getName()) == 0)
                throw new IocasteException("");
        }
        
        dbuser = iocaste.getSystemParameter("db.user");
        query = new StringBuilder("grant select, insert, update, delete on ").
                append(model.getTableName()).
                append(" to ").
                append(dbuser).toString();
        
        return iocaste.update(query);
    }
    
    /**
     * 
     * @param model
     * @param function
     * @param queries
     * @throws Exception
     */
    public static final int update(DocumentModel model, Function function,
            Map<String, Map<String, String>> queries) throws Exception {
        DocumentModel oldmodel = get(model.getName(), function, queries);
        Iocaste iocaste = new Iocaste(function);
        
        for (DocumentModelItem item : model.getItens()) {
            if (!oldmodel.contains(item)) {
                if (DataElementServices.
                        insert(iocaste, item.getDataElement()) == 0)
                    throw new IocasteException("");
                
                if (insertModelItem(iocaste, item) == 0)
                    throw new IocasteException("");
                
                addDBColumn(iocaste, item);
            } else {
                if (updateModelItem(iocaste, item, oldmodel) == 0)
                    throw new IocasteException("");
            }
        }
        
        for (DocumentModelItem olditem : oldmodel.getItens()) {
            if (model.contains(olditem))
                continue;
            if (removeModelItem(iocaste, olditem) == 0)
                throw new IocasteException("");
            if (removeDBColumn(iocaste, olditem) == 0)
                throw new IocasteException("");
        }
        
        Common.parseQueries(model, queries);
        
        return 1;
    }
    
    /**
     * 
     * @param iocaste
     * @param item
     * @param oldmodel
     * @return
     * @throws Exception
     */
    private static final int updateModelItem(Iocaste iocaste,
            DocumentModelItem item, DocumentModel oldmodel) throws Exception {
        StringBuilder sb;
        DataElement ddelement;
        Object[] criteria;
        String shname;
        DocumentModel model = item.getDocumentModel();
        DocumentModelItem reference, olditem = oldmodel.
                getModelItem(item.getName());
        
        String tablename = model.getTableName(),
                oldfieldname = olditem.getTableFieldName(),
                fieldname = item.getTableFieldName();
        
        String query = new StringBuilder("alter table ").
                append(tablename).
                append(" alter column ").toString();
        
        if (iocaste.update("delete from shref where iname = ?",
                Documents.getComposedName(olditem)) == 0)
            return 0;
        
        /*
         * renomeia campo da tabela
         */
        if (!fieldname.equals(oldfieldname)) {
            sb = new StringBuilder(query).
                    append(oldfieldname).
                    append(" rename to ").
                    append(fieldname);
            
            if (iocaste.update(sb.toString()) == 0)
                return 0;
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
        if (iocaste.update(query) == 0)
            return 0;
        
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
                
                if (iocaste.update(query) == 0)
                    return 0;
            }
        } else {
            if (olditem.getReference() != null) {
                query = new StringBuilder("alter table").
                        append(tablename).
                        append(" drop constraint ").
                        append(item.getTableFieldName()).toString();
                
                if (iocaste.update(query) == 0)
                    return 0;
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

        if (iocaste.update(query, criteria) == 0)
            throw new IocasteException("");
        
        query = "update docs002 set docid = ?, index = ?, fname = ?, " +
                "ename = ?, attrb = ?, itref = ? where iname = ?";
        
        criteria = new Object[7];
        
        criteria[0] = model.getName();
        criteria[1] = item.getIndex();
        criteria[2] = item.getTableFieldName();
        criteria[3] = ddelement.getName();
        criteria[4] = item.getAttributeName();
        criteria[5] = (reference == null)?
                null : Documents.getComposedName(reference);
        criteria[6] = Documents.getComposedName(item);
        
        if (iocaste.update(query, criteria) == 0)
            throw new IocasteException("");
        
        shname = item.getSearchHelp();
        if (shname == null)
            return 1;
        
        query = "insert into shref(iname, shcab) values(? ,?)";
        if (iocaste.update(query, criteria[7], shname) == 0)
            throw new IocasteException("");
        
        return 1;
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
