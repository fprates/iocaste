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
    private static final byte DOCUMENT = 0;
    private static final byte DOC_ITEM = 1;
    private static final byte SH_REFERENCE = 2;
    private static final byte TABLE_INDEX = 3;
    private static final byte INS_ITEM = 4;
    private static final byte INS_FOREIGN = 5;
    private static final byte SH_HEADER = 6;
    private static final byte INS_SH_REF = 7;
    private static final byte DEL_KEY = 8;
    private static final byte DEL_MODEL_REF = 9;
    private static final byte DEL_MODEL = 10;
    private static final byte DEL_FOREIGN = 11;
    private static final byte DEL_SH_REF = 12;
    private static final byte SH_ITEM = 13;
    private static final byte SH_HEAD_EXPRT = 14;
    private static final byte DEL_ITEM = 15;
    private static final byte DEL_ELEMENT = 16;
    private static final byte ELEMENT = 17;
    private static final byte INS_KEY = 18;
    private static final byte INS_HEADER = 19;
    private static final byte INS_MODEL_REF = 20;
    private static final byte UPDATE_ELEMENT = 21;
    private static final byte UPDATE_ITEM = 22;
    
    private static final String[] QUERIES = {
        "select * from DOCS001 where docid = ?",
        "select * from DOCS002 where docid = ?",
        "select * from SHREF where iname = ?",
        "select * from DOCS004 where docid = ?",
        "insert into DOCS002(iname, docid, nritm, " +
                "fname, ename, attrb, itref) values(?, ?, ?, ?, ?, ?, ?)",
        "insert into DOCS006(iname, itref) values(?, ?)",
        "select * from SHCAB where ident = ?",
        "insert into SHREF(iname, shcab) values(?, ?)",
        "delete from DOCS004 where iname = ?",
        "delete from DOCS005 where tname = ?",
        "delete from DOCS001 where docid = ?",
        "delete from DOCS006 where iname = ?",
        "delete from SHREF where iname = ?",
        "select * from SHITM where mditm = ?",
        "select * from SHCAB where exprt = ?",
        "delete from DOCS002 where iname = ?",
        "delete from DOCS003 where ename = ?",
        "select * from DOCS003 where ename = ?",
        "insert into DOCS004(iname, docid) values (?, ?)",
        "insert into DOCS001(docid, tname, class) values(?, ?, ?)",
        "insert into DOCS005(tname, docid) values(? , ?)",
        "update DOCS003 set decim = ?, lngth = ?, etype = ?, upcas = ? " +
                "where ename = ?",
        "update DOCS002 set docid = ?, nritm = ?, fname = ?, ename = ?, " +
                "attrb = ?, itref = ? where iname = ?"
    };
    
    /**
     * 
     * @param iocaste
     * @param item
     * @param refstmt
     * @return
     * @throws Exception
     */
    private static final int addDBColumn(Iocaste iocaste,
            DocumentModelItem item, String refstmt) throws Exception {
        DocumentModelItem reference;
        String modelname = item.getDocumentModel().getTableName();
        StringBuilder sb = new StringBuilder("alter table ").append(modelname);
        DataElement ddelement = item.getDataElement();
        
        sb.append(" add column ").append(item.getTableFieldName());
        setDBFieldsString(sb, ddelement);
        
        reference = item.getReference();
        if (reference != null)
            sb.append(refstmt).
                    append(reference.getDocumentModel().getTableName()).
                    append("(").
                    append(reference.getTableFieldName()).append(")");
        
        return iocaste.update(sb.toString());
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
        String itemref, name;
        String[] composed;
        Map<String, Object> columns;
        DocumentModelItem item;
        DocumentModel document = null;
        
        if (documentname == null)
            throw new IocasteException("Document model not specified.");
        
        if (cache.models.containsKey(documentname))
            return cache.models.get(documentname);
        
        iocaste = new Iocaste(cache.function);
        lines = iocaste.selectUpTo(QUERIES[DOCUMENT], 1, documentname);
        if (lines == null)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        document = new DocumentModel();
        document.setName((String)columns.get("DOCID"));
        document.setTableName((String)columns.get("TNAME"));
        document.setClassName((String)columns.get("CLASS"));
        
        lines = iocaste.select(QUERIES[DOC_ITEM], documentname);
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
            item.setIndex(((BigDecimal)columns.get("NRITM")).intValue());
            
            itemref = (String)columns.get("ITREF");
            if (itemref != null) {
                composed = itemref.split("\\.");
                item.setReference(get(composed[0], cache).
                        getModelItem(composed[1]));
            }
            
            shlines = iocaste.select(QUERIES[SH_REFERENCE], name);
            if (shlines != null) {
                columns = (Map<String, Object>)shlines[0];
                item.setSearchHelp((String)columns.get("SHCAB"));
            }
            
            i = item.getIndex();
            document.add(item);
            item.setIndex(i);
        }
        
        lines = iocaste.select(QUERIES[TABLE_INDEX], documentname);
        if (lines != null)
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
    
    private static final String getReferenceStatement(Iocaste iocaste)
            throws Exception {
        String dbtype = iocaste.getSystemParameter("dbtype");
        
        if (dbtype.equals("mysql"))
            return " references ";
        else
            return " foreign key references ";
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
        
        dataelement = item.getDataElement();
        
        tname = Documents.getComposedName(item);
        reference = item.getReference();
        itemref = (reference == null)?
                null : Documents.getComposedName(reference);
        
        if (iocaste.update(QUERIES[INS_ITEM], tname,
                model.getName(),
                item.getIndex(),
                item.getTableFieldName(),
                dataelement.getName(),
                item.getAttributeName(),
                itemref) == 0)
            return 0;
        
        if (itemref != null) {
            if (iocaste.update(QUERIES[INS_FOREIGN], tname, itemref) == 0)
                return 0;
        }
        
        shname = item.getSearchHelp();
        if (shname == null)
            return 1;

        if (iocaste.select(QUERIES[SH_HEADER], shname) == null)
            return 1;

        return iocaste.update(QUERIES[INS_SH_REF], tname, shname);
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
        String tablename, name, query;
        
        for (DocumentModelKey key : model.getKeys()) {
            name = Documents.getComposedName(key.getModel().
                    getModelItem(key.getModelItemName()));
            if (iocaste.update(QUERIES[DEL_KEY], name) == 0)
                throw new IocasteException("");
        }
        
        for (DocumentModelItem item : model.getItens())
            if (removeModelItem(iocaste, item) == 0)
                throw new IocasteException("");
        
        tablename = model.getTableName();
        if (iocaste.update(QUERIES[DEL_MODEL_REF], tablename) == 0)
            throw new IocasteException("error on delete model/table reference");
        
        name = model.getName();
        if (iocaste.update(QUERIES[DEL_MODEL], name) == 0)
            throw new IocasteException("error on delete header model data");
        
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
        String error;
        String name = Documents.getComposedName(item);
        
        iocaste.update(QUERIES[DEL_FOREIGN], name);
        
        iocaste.update(QUERIES[DEL_SH_REF], name);

        error = "there is search help dependence on item ";
        error = new StringBuilder(error).append(name).toString();
        
        if (iocaste.selectUpTo(QUERIES[SH_ITEM], 1, name) != null)
            throw new IocasteException(error);

        if (iocaste.selectUpTo(QUERIES[SH_HEAD_EXPRT], 1, name) != null)
            throw new IocasteException(error);

        if (iocaste.update(QUERIES[DEL_ITEM], name) == 0)
            throw new IocasteException("error on removing model item");

        iocaste.update(QUERIES[DEL_ELEMENT], name);
        
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
        
        for (DocumentModelItem item : itens) {
            element = item.getDataElement();
            
            if (element == null)
                throw new IocasteException(new StringBuilder(item.getName()).
                        append(" has null data element.").toString());
            
            if (iocaste.selectUpTo(
                    QUERIES[ELEMENT], 1, element.getName()) != null)
                continue;
            
            DataElementServices.insert(iocaste, element);
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
        String name = model.getName();
        String tablename = model.getTableName();
        
        if (iocaste.update(QUERIES[INS_HEADER],
                name, tablename, model.getClassName()) == 0)
            throw new IocasteException("document header generation error");

        if (iocaste.update(QUERIES[INS_MODEL_REF], tablename, name) == 0)
            throw new IocasteException("document header generation error");
        
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
        String refstmt = getReferenceStatement(iocaste);
        
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
                sb.append(refstmt).
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
        String name;
        
        for (DocumentModelKey key : model.getKeys()) {
            name = Documents.getComposedName(model.
                    getModelItem(key.getModelItemName()));
            
            if (iocaste.update(
                    QUERIES[INS_KEY], name, key.getModel().getName()) == 0)
                throw new IocasteException("Error through key insert.");
        }
        
        return 1;
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
        String refstmt = getReferenceStatement(iocaste);
        
        for (DocumentModelItem item : model.getItens()) {
            if (!oldmodel.contains(item)) {
                if (DataElementServices.
                        insert(iocaste, item.getDataElement()) == 0)
                    throw new IocasteException("");
                
                if (insertModelItem(iocaste, item) == 0)
                    throw new IocasteException("Error through model insert");
                
                addDBColumn(iocaste, item, refstmt);
            } else {
                if (updateModelItem(iocaste, item, oldmodel) == 0)
                    throw new IocasteException("Error through model update");
            }
        }
        
        for (DocumentModelItem olditem : oldmodel.getItens()) {
            if (model.contains(olditem))
                continue;
            
            if (removeModelItem(iocaste, olditem) == 0)
                throw new IocasteException(
                        "Model.update(): removeModelItem() fail");
            
            if (removeDBColumn(iocaste, olditem) == 0)
                throw new IocasteException(
                        "Model.update():removeDBColumn() fail");
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
        
        iocaste.update(QUERIES[DEL_SH_REF],
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
        criteria = new Object[5];
        
        criteria[0] = ddelement.getDecimals();
        criteria[1] = ddelement.getLength();
        criteria[2] = ddelement.getType();
        criteria[3] = ddelement.isUpcase();
        criteria[4] = ddelement.getName();

        if (iocaste.update(QUERIES[UPDATE_ELEMENT], criteria) == 0)
            throw new IocasteException(
                    "Model.updateModelItem(): UPDATE_ELEMENT fail");
        
        criteria = new Object[7];
        
        criteria[0] = model.getName();
        criteria[1] = item.getIndex();
        criteria[2] = item.getTableFieldName();
        criteria[3] = ddelement.getName();
        criteria[4] = item.getAttributeName();
        criteria[5] = (reference == null)?
                null : Documents.getComposedName(reference);
        criteria[6] = Documents.getComposedName(item);
        
        if (iocaste.update(QUERIES[UPDATE_ITEM], criteria) == 0)
            throw new IocasteException(
                    "Model.updateModelItem(): UPDATE_ITEM fail");
        
        shname = item.getSearchHelp();
        if (Common.isInitial(shname))
            return 1;
        
        if (iocaste.update(QUERIES[INS_SH_REF], criteria[6], shname) == 0)
            throw new IocasteException(
                    "Model.updateModelItem(): INS_SH_REF fail");
        
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
