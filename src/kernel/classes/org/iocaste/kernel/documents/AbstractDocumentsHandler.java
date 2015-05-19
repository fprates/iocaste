package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.kernel.config.GetSystemParameter;
import org.iocaste.kernel.database.Select;
import org.iocaste.kernel.database.Update;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public abstract class AbstractDocumentsHandler extends AbstractHandler {
    protected static final byte DOCUMENT = 0;
    protected static final byte DOC_ITEM = 1;
    protected static final byte SH_REFERENCE = 2;
    protected static final byte TABLE_INDEX = 3;
    protected static final byte INS_ITEM = 4;
    protected static final byte INS_FOREIGN = 5;
    protected static final byte SH_HEADER = 6;
    protected static final byte INS_SH_REF = 7;
    protected static final byte DEL_KEY = 8;
    protected static final byte DEL_MODEL_REF = 9;
    protected static final byte DEL_MODEL = 10;
    protected static final byte DEL_FOREIGN = 11;
    protected static final byte DEL_SH_REF = 12;
    protected static final byte SH_ITEM = 13;
    protected static final byte SH_HEAD_EXPRT = 14;
    protected static final byte DEL_ITEM = 15;
    protected static final byte DEL_ELEMENT = 16;
    protected static final byte ELEMENT = 17;
    protected static final byte INS_KEY = 18;
    protected static final byte INS_HEADER = 19;
    protected static final byte INS_MODEL_REF = 20;
    protected static final byte UPDATE_ELEMENT = 21;
    protected static final byte UPDATE_ITEM = 22;
    protected static final byte INS_ELEMENT = 23;
    protected static final byte RANGE = 24;
    protected static final byte UPDATE_RANGE = 25;
    protected static final byte RANGE_SERIE = 26;
    protected static final byte UPDATE_SERIES = 27;
    protected static final String[] QUERIES = {
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
        "insert into DOCS001(docid, tname, class, nscol, nstyp, nslen, pkgnm) "+
                "values(?, ?, ?, ?, ?, ?, ?)",
        "insert into DOCS005(tname, docid) values(? , ?)",
        "update DOCS003 set decim = ?, lngth = ?, etype = ?, upcas = ? " +
                "where ename = ?",
        "update DOCS002 set docid = ?, nritm = ?, fname = ?, ename = ?, " +
                "attrb = ?, itref = ? where iname = ?",
        "insert into DOCS003(ename, decim, lngth, etype, upcas, atype) " +
                "values(?, ?, ?, ?, ?, ?)",
        "select CRRNT from RANGE001 where ident = ? and nmspc = ?",
        "update RANGE001 set crrnt = ? where ident = ? and nmspc = ?",
        "select CRRNT from RANGE002 where SERIE = ? and NMSPC = ?",
        "update RANGE002 set crrnt = ? where SERIE = ? and NMSPC = ?"
    };
    
    protected final int addTableColumn(Connection connection, String refstmt,
            DocumentModelItem item, String dbtype) throws Exception {
        DocumentModelItem reference;
        String modelname = item.getDocumentModel().getTableName();
        StringBuilder sb = new StringBuilder("alter table ").append(modelname);
        DataElement ddelement = item.getDataElement();
        
        sb.append(" add column ").append(item.getTableFieldName());
        setTableFieldsString(sb, ddelement, dbtype);
        
        reference = item.getReference();
        if (reference != null)
            sb.append(refstmt).
                    append(reference.getDocumentModel().getTableName()).
                    append("(").
                    append(reference.getTableFieldName()).append(")");
        
        return update(connection, sb.toString());
    }
    
    /**
     * Retorna nome composto.
     * @param item item de modelo
     * @return nome composto do item.
     */
    protected final String getComposedName(DocumentModelItem item) {
        return new StringBuilder(item.getDocumentModel().getName()).
                append(".").append(item.getName()).toString();
    }
    
    /**
     * 
     * @param model
     * @param line
     * @return
     */
    protected final ExtendedObject getExtendedObjectFrom(
            DocumentModel model, Map<String, Object> line) {
        DocumentModelItem nsitem;
        Object value;
        ExtendedObject object = new ExtendedObject(model);
        
        for (DocumentModelItem modelitem : model.getItens()) {
            value = line.get(modelitem.getTableFieldName());
            object.set(modelitem, value);
        }
        
        nsitem = model.getNamespace();
        if (nsitem != null)
            object.setNS(line.get(nsitem.getTableFieldName()));
        
        return object;
    }
    
    /**
     * 
     * @param queryinfo
     * @param line
     * @return
     */
    protected final ExtendedObject getExtendedObject2From(Connection connection,
            Query query, Map<String, Object> line) throws Exception {
        GetDocumentModel getmodel;
        DocumentModel model;
        DocumentModelItem item, itemref;
        String[] composed;
        int i;
        Documents documents;
        
        documents = getFunction();
        getmodel = documents.get("get_document_model");
        if (query.getJoins().size() == 0) {
            model = getmodel.run(connection, documents, query.getModel());
            return getExtendedObjectFrom(model, line);
        }

        model = new DocumentModel(null);
        i = 0;
        for (String column : query.getColumns()) {
            composed = column.split("\\.");
            item = new DocumentModelItem(composed[1]);
            itemref = ((DocumentModel)getmodel.run(
                    connection, documents, composed[0])).
                    getModelItem(composed[1]);
            item.setTableFieldName(itemref.getTableFieldName());
            item.setIndex(i++);
            item.setDataElement(itemref.getDataElement());
            item.setDocumentModel(model);
            model.add(item);
        }
        
        return getExtendedObjectFrom(model, line);
    }
    
    private final DocumentModelItem getModelItem(Connection connection,
            Documents documents, DocumentModel model, String name)
                    throws Exception {
        GetDocumentModel getmodel = documents.get("get_document_model");
        
        return getmodel.run(connection, documents, model.getName()).
                getModelItem(name);
    }
    
    protected final DocumentModelItem getModelKey(DocumentModel model) {
        for (DocumentModelKey key : model.getKeys())
            return model.getModelItem(key.getModelItemName());
        return null;
    }
    
    protected final String getSystemParameter(Documents documents, String name)
    {
        GetSystemParameter getparameter;
        getparameter = documents.config.get("get_system_parameter");
        return getparameter.run(name);
    }
    
    protected final DocumentModelItem getReferenceItem(
            DocumentModel model, DocumentModelItem key) {
        DocumentModelItem reference;
        
        for (DocumentModelItem item : model.getItens()) {
            reference = item.getReference();
            if ((reference == null) || (!reference.equals(key)))
                continue;
            
            return item;
        }
        
        return null;
    }
    
    protected final String getReferenceStatement(Documents documents)
            throws Exception {
        String dbtype = getSystemParameter(documents, "dbtype");
        if (dbtype.equals("mysql") || dbtype.equals("postgres"))
            return " references ";
        else
            return " foreign key references ";
    }
    
    protected final int insertModelItem(Connection connection,
            DocumentModelItem item) throws Exception {
        DocumentModelItem reference;
        DataElement dataelement;
        String itemref, tname, shname;
        DocumentModel model = item.getDocumentModel();
        
        dataelement = item.getDataElement();
        tname = getComposedName(item);
        reference = item.getReference();
        if (reference != null) {
            itemref = getComposedName(reference);
            if (model.getName().equals(reference.getDocumentModel().getName()))
                throw new IocasteException(
                        new StringBuilder("Self model reference for ").
                            append(tname).toString());
        } else {
            itemref = null;
        }
        
        if (update(connection, QUERIES[INS_ITEM], tname,
                model.getName(),
                item.getIndex(),
                item.getTableFieldName(),
                dataelement.getName(),
                item.getAttributeName(),
                itemref) == 0)
            return 0;
        
        if (itemref != null)
            if (update(connection, QUERIES[INS_FOREIGN], tname, itemref) == 0)
                return 0;
        
        shname = item.getSearchHelp();
        if (shname == null)
            return 1;

        if (select(connection, QUERIES[SH_HEADER], 0, shname) == null)
            return 1;

        return update(connection, QUERIES[INS_SH_REF], tname, shname);
    }

    protected void prepareElements(Connection connection, Documents documents,
            DocumentModel model) throws Exception {
        DocumentModelItem reference;
        DataElement element;
        CreateDataElement createde;
        GetDataElement getde;
        
        createde = documents.get("create_data_element");
        getde = documents.get("get_data_element");
        for (DocumentModelItem item : model.getItens()) {
            element = item.getDataElement();
            if (element == null) {
                reference = item.getReference();
                if (reference == null)
                    throw new RuntimeException(
                            item.getName().concat(
                                    " has an undefined element or reference."));
                if (reference.isDummy())
                    reference = getModelItem(connection, documents,
                            reference.getDocumentModel(), reference.getName());
                element = reference.getDataElement();
                item.setDataElement(element);
            }
            
            if (element == null)
                throw new IocasteException(new StringBuilder(item.getName()).
                        append(" has null data element.").toString());
            
            if (element.isDummy()) {
                element = getde.run(connection, element.getName());
                item.setDataElement(element);
            } else {
                createde.prepare(element);
            }   
        }
    }
    
    @Override
    public abstract Object run(Message message) throws Exception;
    
    protected Object[] select(Connection connection, String query, int rows,
            Object... criteria) throws Exception {
        Documents documents = getFunction();
        Select select = documents.database.get("select");
        return select.run(connection, query, rows, criteria);
    }
    
    /**
     * 
     * @param sb
     * @param ddelement
     */
    protected void setTableFieldsString(StringBuilder sb,
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
    
    protected final int update(Connection connection, String query,
            Object... criteria) throws Exception {
        Documents documents = getFunction();
        Update update = documents.database.get("update");
        return update.run(connection, query, criteria);
    }

}
