package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.HashMap;
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
            DocumentModelItem item, String refstmt, String dbtype)
                    throws Exception {
        DocumentModelItem reference;
        String modelname = item.getDocumentModel().getTableName();
        StringBuilder sb = new StringBuilder("alter table ").append(modelname);
        DataElement ddelement = item.getDataElement();
        
        sb.append(" add column ").append(item.getTableFieldName());
        setDBFieldsString(sb, ddelement, dbtype);
        
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
        
        registerModel(model, cache);
        if (model.getTableName() != null)
            createTable(model, cache);
        
        name = model.getName();
        model.setQueries(cache.queries.get(name));
        cache.models.put(name, model);
        
        return 1;
    }
    
    /**
     * 
     * @param model
     * @param cache
     * @return
     * @throws Exception
     */
    private static final int createTable(DocumentModel model,
            Cache cache) throws Exception {
        DocumentModel refmodel;
        DataElement dataelement;
        DocumentModelItem reference;
        StringBuilder sb, sbk = null;
        String tname, query, refname;
        DocumentModelItem[] itens = model.getItens();
        Iocaste iocaste = new Iocaste(cache.function);
        String refstmt = getReferenceStatement(iocaste);
        String dbtype = iocaste.getSystemParameter("dbtype");
        int size = itens.length - 1;
        
        sb = new StringBuilder("create table ").append(
                model.getTableName()).append("(");
        
        for (DocumentModelItem item : itens) {
            tname = item.getTableFieldName();
            if (tname == null)
                throw new IocasteException("Table field name is null.");
            
            sb.append(tname);
            dataelement = item.getDataElement();
            if (dataelement.isDummy()) {
                dataelement = DataElementServices.
                        get(iocaste, dataelement.getName());
                item.setDataElement(dataelement);
            }

            setDBFieldsString(sb, dataelement, dbtype);
            if (model.isKey(item)) {
                if (sbk == null)
                    sbk = new StringBuilder(", primary key(");
                else
                    sbk.append(", ");
                
                sbk.append(tname);
            }
            
            reference = item.getReference();
            if (reference != null) {
                if (reference.isDummy()) {
                    refname = reference.getDocumentModel().getName();
                    refmodel = Model.get(refname, cache);
                    
                    refname = Documents.getComposedName(reference);
                    reference = refmodel.getModelItem(reference.getName());
                    if (reference == null)
                        throw new IocasteException(new StringBuilder(refname).
                                append(": is an invalid reference.").
                                toString());
                }
                
                sb.append(refstmt).append(reference.getDocumentModel().
                        getTableName()).append("(").
                        append(reference.getTableFieldName()).append(")");
            }
            
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
        document = new DocumentModel((String)columns.get("DOCID"));
        document.setTableName((String)columns.get("TNAME"));
        document.setClassName((String)columns.get("CLASS"));
        
        lines = iocaste.select(QUERIES[DOC_ITEM], documentname);
        for (Object object : lines) {
            columns = (Map<String, Object>)object;
            name = (String)columns.get("INAME");
            composed = name.split("\\.");
            
            item = new DocumentModelItem(composed[1]);
            item.setDocumentModel(document);
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
        
        if (dbtype.equals("mysql") || dbtype.equals("postgres"))
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
        
        if (itemref != null)
            if (iocaste.update(QUERIES[INS_FOREIGN], tname, itemref) == 0)
                return 0;
        
        shname = item.getSearchHelp();
        if (shname == null)
            return 1;

        if (iocaste.select(QUERIES[SH_HEADER], shname) == null)
            return 1;

        return iocaste.update(QUERIES[INS_SH_REF], tname, shname);
    }
    
    /**
     * 
     * @param iocaste
     * @param model
     * @return
     * @throws Exception
     */
    private static final int registerDataElements(Iocaste iocaste,
            DocumentModel model) throws Exception {
        String name;
        DataElement element;
        DocumentModelItem[] itens = model.getItens();
        
        for (DocumentModelItem item : itens) {
            element = item.getDataElement();
            if (element == null)
                throw new IocasteException(new StringBuilder(item.getName()).
                        append(" has null data element.").toString());
            
            switch (element.getType()) {
            case DataType.DATE:
                element.setLength(10);
                element.setDecimals(0);
                element.setUpcase(false);
                
                break;
            case DataType.TIME:
                element.setLength(8);
                element.setDecimals(0);
                element.setUpcase(false);
                
                break;
            case DataType.BOOLEAN:
                element.setLength(1);
                element.setDecimals(0);
                element.setUpcase(false);
                
                break;
            }
            
            name = element.getName();
            if (iocaste.selectUpTo(QUERIES[ELEMENT], 1, name) != null)
                continue;
            
            if (element.isDummy())
                throw new IocasteException(new StringBuilder(name).
                        append(": is an invalid data element.").toString());
            
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
    private static final int registerDocumentHeader(Iocaste iocaste,
            DocumentModel model, Cache cache) throws Exception {
        int l;
        String name = model.getName();
        String tablename = model.getTableName();
        if (cache.mmodel == null)
            cache.mmodel = get("MODEL", cache);
        
        if (cache.mmodel != null) {
            l = Common.getModelItemLen("NAME", cache);
            if (name.length() > l)
                throw new IocasteException(name.concat(": " +
                		"invalid modelname length on document header"));
            
            if (tablename != null) {
                l = Common.getModelItemLen("TABLE", cache);
                if (tablename.length() > l)
                    throw new IocasteException(tablename.concat(": " +
                    		"invalid tablename length on document header"));
            }
        }
        
        if (iocaste.update(QUERIES[INS_HEADER],
                name, tablename, model.getClassName()) == 0)
            throw new IocasteException("document header insert error");

        if (tablename != null)
            if (iocaste.update(QUERIES[INS_MODEL_REF], tablename, name) == 0)
                throw new IocasteException(
                        "header's model reference insert error");
        
        return 1;
    }
    
    /**
     * 
     * @param model
     * @param cache
     * @param iocaste
     * @return
     * @throws Exception
     */
    private static final int registerDocumentItens(DocumentModel model,
            Cache cache, Iocaste iocaste) throws Exception {
        DataElement dataelement;
        DocumentModelItem[] itens = model.getItens();
        
        for (DocumentModelItem item : itens) {
            insertModelItem(iocaste, item);
            
            dataelement = item.getDataElement();
            if (!dataelement.isDummy())
                continue;
            
            dataelement = DataElementServices.
                    get(iocaste, dataelement.getName());
            item.setDataElement(dataelement);
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
    private static int registerDocumentKeys(Iocaste iocaste,
            DocumentModel model) throws Exception {
        String modelname, name;
        
        modelname = model.getName();
        for (DocumentModelKey key : model.getKeys()) {
            name = Documents.getComposedName(model.
                    getModelItem(key.getModelItemName()));
            
            if (iocaste.update(QUERIES[INS_KEY], name, modelname) == 0)
                throw new IocasteException("error on key insert.");
        }
        
        return 1;
    }
    
    /**
     * 
     * @param model
     * @param cache
     * @throws Exception
     */
    private static final void registerModel(DocumentModel model, Cache cache)
            throws Exception {
        Iocaste iocaste = new Iocaste(cache.function);

        registerDataElements(iocaste, model);
        registerDocumentHeader(iocaste, model, cache);
        registerDocumentItens(model, cache, iocaste);
        registerDocumentKeys(iocaste, model);
        Common.parseQueries(model, cache.queries);
        
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
        String tablename, modelname, name, query;
        Iocaste iocaste;
        
        modelname = model.getName();
        if (get(modelname, cache) == null)
            return 0;
        
        iocaste = new Iocaste(cache.function);
        for (DocumentModelKey key : model.getKeys()) {
            name = Documents.getComposedName(model.getModelItem(
                    key.getModelItemName()));
            if (iocaste.update(QUERIES[DEL_KEY], name) == 0)
                throw new IocasteException("error on removing model key");
        }
        
        for (DocumentModelItem item : model.getItens())
            if (removeModelItem(iocaste, item) == 0)
                throw new IocasteException("error on removing model item");
        
        tablename = model.getTableName();
        if ((tablename != null) &&
                (iocaste.update(QUERIES[DEL_MODEL_REF], tablename) == 0))
            throw new IocasteException(
                        "error on removing model/table reference");
        
        if (iocaste.update(QUERIES[DEL_MODEL], modelname) == 0)
            throw new IocasteException("error on removing header model data");
        
        cache.queries.remove(modelname);
        cache.models.remove(modelname);
        
        if (tablename == null)
            return 1;
        
        query = new StringBuilder("drop table ").append(tablename).
                toString();
        iocaste.update(query);
        
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
        DocumentModelItem newitem;
        Map<String, String> queries;
        DocumentModel newmodel, oldmodel = Model.get(oldname, cache);
        
        newmodel = new DocumentModel(newname);
        newmodel.setTableName(oldmodel.getTableName());
        newmodel.setClassName(oldmodel.getClassName());
        for (DocumentModelItem olditem : oldmodel.getItens()) {
            newitem = new DocumentModelItem(olditem.getName());
            newitem.setAttributeName(olditem.getAttributeName());
            newitem.setDataElement(olditem.getDataElement());
            newitem.setDummy(olditem.isDummy());
            newitem.setReference(olditem.getReference());
            newitem.setSearchHelp(olditem.getSearchHelp());
            newitem.setTableFieldName(olditem.getTableFieldName());
            
            newmodel.add(newitem);
            if (oldmodel.isKey(olditem))
                newmodel.add(new DocumentModelKey(newitem));
        }
        
        queries = new HashMap<>();
        for (String name : new String[] {"insert", "delete", "update"})
            queries.put(name, oldmodel.getQuery(name));
        newmodel.setQueries(queries);
        
        if (Model.create(newmodel, cache) == 0)
            throw new IocasteException("error on rename model (step 1)");
        
        if (remove(oldmodel, cache) == 0)
            throw new IocasteException("error on rename model (step 2)");
        
        return 1;
    }
    
    /**
     * 
     * @param sb
     * @param ddelement
     */
    public static void setDBFieldsString(StringBuilder sb,
            DataElement ddelement, String dbtype) throws Exception {
        int length = ddelement.getLength();
        
        switch (ddelement.getType()) {
        case DataType.CHAR:
            if (length == 0)
                throw new IocasteException(new StringBuilder("Invalid "
                        + "length for data element ").
                        append(ddelement.getName()).toString());
            
            sb.append(" varchar(");
            sb.append(length);
            sb.append(")");
            break;
        case DataType.NUMC:
            if (length == 0)
                throw new IocasteException(new StringBuilder("Invalid "
                        + "length for data element ").
                        append(ddelement.getName()).toString());
            
            sb.append(" numeric(");
            sb.append(length);
            sb.append(")");
            break;
        case DataType.DEC:
            if (length == 0)
                throw new IocasteException(new StringBuilder("Invalid "
                        + "length for data element ").
                        append(ddelement.getName()).toString());
            
            sb.append(" decimal(");
            sb.append(length);
            sb.append(",");
            sb.append(ddelement.getDecimals());
            sb.append(")");
            break;
        case DataType.DATE:
            sb.append(" date");
            break;
        case DataType.TIME:
            sb.append(" time");
            break;
        case DataType.BOOLEAN:
            if (dbtype.equals("postgres"))
                sb.append(" boolean");
            else
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
        String dbtype = iocaste.getSystemParameter("dbtype");
        
        for (DocumentModelItem item : model.getItens()) {
            if (!oldmodel.contains(item)) {
                DataElementServices.insert(iocaste, item.getDataElement());                
                if (insertModelItem(iocaste, item) == 0)
                    throw new IocasteException("error on model insert");
                
                if (item.getTableFieldName() != null)
                    addDBColumn(iocaste, item, refstmt, dbtype);
            } else {
                if (updateModelItem(iocaste, item, oldmodel) == 0)
                    throw new IocasteException("error on model update");
            }
        }
        
        for (DocumentModelItem olditem : oldmodel.getItens()) {
            if (model.contains(olditem))
                continue;
            
            if (removeModelItem(iocaste, olditem) == 0)
                throw new IocasteException("error on remove model item");
            
            if (olditem.getTableFieldName() != null)
                if (removeDBColumn(iocaste, olditem) == 0)
                    throw new IocasteException("error on remove table column");
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
        String query, shname;
        StringBuilder sb;
        DataElement ddelement;
        Object[] criteria;
        DocumentModel model = item.getDocumentModel();
        DocumentModelItem reference, olditem = oldmodel.
                getModelItem(item.getName());
        String tablename = model.getTableName();
        String oldfieldname = olditem.getTableFieldName();
        String fieldname = item.getTableFieldName();
        String dbtype = iocaste.getSystemParameter("dbtype");
        
        iocaste.update(QUERIES[DEL_SH_REF],
                Documents.getComposedName(olditem));
        
        /*
         * renomeia campo da tabela
         */
        sb = new StringBuilder("alter table ").append(tablename);
        dbtype = iocaste.getSystemParameter("dbtype");
        switch (dbtype) {
        case "hsqldb":
            query = sb.append(" alter column ").toString();
            break;
        default:
            query = sb.append(" modify column ").toString();
            break;
        }
        
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
        setDBFieldsString(sb, ddelement, dbtype);
        
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
                    "error on update data element");
        
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
                    "error on update model item");
        
        shname = item.getSearchHelp();
        if (Common.isInitial(shname))
            return 1;
        
        if (iocaste.update(QUERIES[INS_SH_REF], criteria[6], shname) == 0)
            throw new IocasteException(
                    "error on insert sh reference");
        
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
        ExtendedObject link = Select.get(tablemodel, model.getTableName(),
                cache.function);
        
        return (link != null)? Documents.TABLE_ALREADY_ASSIGNED : 0;
    }

}