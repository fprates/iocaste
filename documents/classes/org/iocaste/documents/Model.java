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

        setDBFieldsString(sb, ddelement);
        
        reference = item.getReference();
        if (reference != null) {
            sb.append(" foreign key references ").append(reference.
                    getDocumentModel().getTableName()).append("(").
                    append(reference.getTableFieldName()).append(")");
        }
        
        query = sb.toString();
        return iocaste.update(query);
    }
    
    /**
     * 
     * @param model
     * @param cache
     * @return
     * @throws Exception
     */
    public static final int create(DocumentModel model, Cache cache)
            throws Exception {
        String name;
        Iocaste iocaste = new Iocaste(cache.function);

        saveDataElements(iocaste, model);
        saveDocumentHeader(iocaste, model);
        saveDocumentItens(iocaste, model);
        saveDocumentKeys(iocaste, model);
        
        Common.parseQueries(model, cache.queries);
        
        name = model.getName();
        if (cache.models.containsKey(name))
            cache.models.remove(name);
        
        model.setQueries(cache.queries.get(name));
        cache.models.put(name, model);
        
        return 1;
    }
    
    /**
     * 
     * @param documentname
     * @param cache
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final DocumentModel get(String documentname, Cache cache)
            throws Exception {
        int i;
        Iocaste iocaste;
        Object[] lines, shlines;
        String itemref, query, name;
        String[] composed;
        Map<String, Object> columns;
        DocumentModelItem item;
        DocumentModel document = null;
        
        if (documentname == null)
            throw new Exception("Document model not specified.");
        
        if (cache.models.containsKey(documentname))
            return cache.models.get(documentname);
        
        iocaste = new Iocaste(cache.function);

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
                item.setReference(get(composed[0], cache).
                        getModelItem(composed[1]));
            }
            
            query = "select * from shref where iname = ?";
            shlines = iocaste.select(query, name);
            if (shlines != null && shlines.length > 0) {
                columns = (Map<String, Object>)shlines[0];
                item.setSearchHelp((String)columns.get("SHCAB"));
            }
            
            i = item.getIndex();
            document.add(item);
            item.setIndex(i);
        }
        
        lines = iocaste.select("select * from docs004 where docid = ?",
                documentname);
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            composed = ((String)columns.get("INAME")).split("\\.");
            
            document.add(new DocumentModelKey(composed[1]));
        }
        
        if (!cache.queries.containsKey(documentname))
            Common.parseQueries(document, cache.queries);
        
        document.setQueries(cache.queries.get(documentname));
        
        cache.models.put(documentname, document);
        
        return document;
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
        itemref = (reference == null)?
                null : Documents.getComposedName(reference);
        
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
     * @param cache
     * @return
     * @throws Exception
     */
    public static final int remove(DocumentModel model, Cache cache)
            throws Exception {
        Iocaste iocaste = new Iocaste(cache.function);
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
        
        cache.queries.remove(name);
        cache.models.remove(name);
        
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
        String error, query = "delete from docs006 where iname = ?";
        String name = Documents.getComposedName(item);
        
        iocaste.update(query, name);
        
        query = "delete from shref where iname = ?";
        iocaste.update(query, name);

        error = "there is search help dependence on item ";
        error = new StringBuilder(error).append(name).toString();
        
        query = "select * from shitm where mditm = ?";
        if (iocaste.select(query, name).length > 0)
            throw new IocasteException(error);
            
        query = "select * from shcab where exprt = ?";
        if (iocaste.select(query, name).length > 0)
            throw new IocasteException(error);
        
        query = "delete from docs002 where iname = ?";
        if (iocaste.update(query, name) == 0)
            throw new IocasteException("error on removing model item");
        
        query = "delete from docs003 where ename = ?";
        if (iocaste.update(query, item.getDataElement().getName()) == 0)
            throw new IocasteException("erro on removing data element");
        
        return 1;
    }
    
    /**
     * 
     * @param oldname
     * @param newname
     * @param cache
     * @return
     * @throws Exception
     */
    public static final int rename(String oldname, String newname, Cache cache)
            throws Exception {
        DocumentModel model = Model.get(oldname, cache);
        
        model.setName(newname);
        if (Model.create(model, cache) == 0)
            throw new IocasteException("");
        
        model.setName(oldname);
        if (remove(model, cache) == 0)
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
        
        query = "insert into docs001(docid, tname, class) values(?, ?, ?)";
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
                append("(");
        
        size = itens.length - 1;
        
        for (DocumentModelItem item : itens) {
            insertModelItem(iocaste, item);
            
            tname = item.getTableFieldName();
            if (tname == null)
                throw new IocasteException("Table field name is null.");
            
            sb.append(tname);
            
            dataelement = item.getDataElement();
            setDBFieldsString(sb, dataelement);
            
            if (model.isKey(item)) {
                if (sbk == null)
                    sbk = new StringBuilder(", primary key(");
                else
                    sbk.append(", ");
                
                sbk.append(tname);
            }
            
            reference = item.getReference();
            if (reference != null)
                sb.append(" foreign key references ").
                        append(reference.getDocumentModel().getTableName()).
                        append("(").
                        append(reference.getTableFieldName()).append(")");
            
            if (size != item.getIndex())
                sb.append(", ");
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
     * @param sb
     * @param ddelement
     */
    public static void setDBFieldsString(StringBuilder sb,
            DataElement ddelement) {
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
            ddelement.setLength(10);
            ddelement.setDecimals(0);
            ddelement.setUpcase(false);
            
            sb.append(" date");
            
            break;
        case DataType.BOOLEAN:
            ddelement.setLength(1);
            ddelement.setDecimals(0);
            ddelement.setUpcase(false);
            
            sb.append(" bit");
            
            break;
        }
    }
    
    /**
     * 
     * @param model
     * @param cache
     * @return
     * @throws Exception
     */
    public static final int update(DocumentModel model, Cache cache)
            throws Exception {
        String name = model.getName();
        DocumentModel oldmodel = get(name, cache);
        Iocaste iocaste = new Iocaste(cache.function);
        
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
        
        Common.parseQueries(model, cache.queries);
        
        cache.models.remove(name);
        cache.models.put(name, model);
        
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
        DocumentModel model = item.getDocumentModel();
        DocumentModelItem reference, olditem = oldmodel.
                getModelItem(item.getName());
        
        String query, shname, tablename = model.getTableName(),
                oldfieldname = olditem.getTableFieldName(),
                fieldname = item.getTableFieldName();
        
        iocaste.update("delete from shref where iname = ?",
                Documents.getComposedName(olditem));
        
        /*
         * renomeia campo da tabela
         */
        
        query = new StringBuilder("alter table ").
                append(tablename).
                append(" alter column ").toString();
        
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
        setDBFieldsString(sb, ddelement);
        
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
        if (Common.isInitial(shname))
            return 1;
        
        query = "insert into shref(iname, shcab) values(? ,?)";
        if (iocaste.update(query, criteria[6], shname) == 0)
            throw new IocasteException("");
        
        return 1;
    }
    
    /**
     * 
     * @param model
     * @param cache
     * @return
     * @throws Exception
     */
    public static final int validate(DocumentModel model, Cache cache)
            throws Exception {
        DocumentModel tablemodel = Model.get("TABLE_MODEL", cache);
        ExtendedObject link = Query.get(tablemodel, model.getTableName(),
                cache.function);
        
        return (link != null)? Documents.TABLE_ALREADY_ASSIGNED : 0;
    }

}
