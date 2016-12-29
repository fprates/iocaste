package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
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
import org.iocaste.kernel.documents.dataelement.GetDataElement;
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
//    protected static final byte INS_SH_REF = 7;
    protected static final byte DEL_KEY = 8;
    protected static final byte DEL_MODEL_REF = 9;
    protected static final byte DEL_MODEL = 10;
    protected static final byte DEL_FOREIGN = 11;
//    protected static final byte DEL_SH_REF = 12;
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
    protected static final byte UPDATE_MODEL_HEAD = 28;
    protected static final String[] QUERIES = {
        "select * from DOCS001 where docid = ?",
        "select * from DOCS002 where docid = ?",
        "select * from SHREF where iname = ?",
        "select * from DOCS004 where docid = ?",
        "insert into DOCS002(itmid, docid, itmnm, " +
                "fname, ename, attrb, itref) values(?, ?, ?, ?, ?, ?, ?)",
        "insert into DOCS006(itmid, itref) values(?, ?)",
        "select * from SHCAB where ident = ?",
        "insert into SHREF(iname, shcab) values(?, ?)",
        "delete from DOCS004 where itmid = ?",
        "delete from DOCS005 where tname = ?",
        "delete from DOCS001 where docid = ?",
        "delete from DOCS006 where itmid = ?",
        "delete from SHREF where iname = ?",
        "select * from SHITM where mditm = ?",
        "select * from SHCAB where exprt = ?",
        "delete from DOCS002 where itmid = ?",
        "delete from DOCS003 where ename = ?",
        "select * from DOCS003 where ename = ?",
        "insert into DOCS004(itmid, docid) values (?, ?)",
        "insert into DOCS001(docid, tname, class, nscol, nstyp, nslen, pkgnm) "+
                "values(?, ?, ?, ?, ?, ?, ?)",
        "insert into DOCS005(tname, docid) values(? , ?)",
        "update DOCS003 set decim = ?, lngth = ?, etype = ?, upcas = ? " +
                "where ename = ?",
        "update DOCS002 set docid = ?, itmnm = ?, fname = ?, ename = ?, " +
                "attrb = ?, itref = ? where itmid = ?",
        "insert into DOCS003(ename, decim, lngth, etype, upcas, atype) " +
                "values(?, ?, ?, ?, ?, ?)",
        "select CRRNT from RANGE001 where ident = ? and nmspc = ?",
        "update RANGE001 set crrnt = ? where ident = ? and nmspc = ?",
        "select CRRNT from RANGE002 where serie = ? and nmspc = ?",
        "update RANGE002 set crrnt = ? where serie = ? and nmspc = ?",
        "update DOCS001 set tname = ?, nscol = ?, nstyp = ?, nslen = ? " +
                "where docid = ?"
    };
    
    protected final void addTableKey(List<String> statements,
            UpdateData data) throws Exception {
        addTableColumn(statements, data, true);
    }
    
    protected final void addTableColumn(List<String> statements,
            UpdateData data) throws Exception {
        addTableColumn(statements, data, false);
    }
    
    protected final void addTableColumn(List<String> statements,
            UpdateData data, boolean key) throws Exception {
        
        if (key)
            data.table.key(
                    data.fieldname,
                    data.element.getType(),
                    data.element.getLength(),
                    data.element.getDecimals());
        else
            data.table.add(
                    data.fieldname,
                    data.element.getType(),
                    data.element.getLength(),
                    data.element.getDecimals());

        if (data.reference != null)
            addTableColumnReference(data, data.reference);
    }
    
    protected final void addTableColumnReference(
            UpdateData data, DocumentModelItem reference) {
        String constraint, tableref;
        
        tableref = reference.getDocumentModel().getTableName();
        constraint = getColumnReferenceName(tableref, data);
        data.table.constraint(
                constraint,
                tableref,
                data.fieldname,
                reference.getTableFieldName());
    }
    
    protected final ExtendedObject cmodelHeaderInstance(Connection connection,
            Documents documents, GetDocumentModel getmodel, ComplexModel cmodel)
                    throws Exception {
        DocumentModel model = getmodel.run(
                connection, documents, "COMPLEX_MODEL");
        ExtendedObject object = new ExtendedObject(model);
        String cmodelname = cmodel.getName();
        object.set("NAME", cmodelname);
        object.set("MODEL", cmodel.getHeader().getName());
        return object;
    }
    
    protected final ExtendedObject cmodelItemInstance(DocumentModel model,
            String cmodelname, ComplexModelItem cmodelitem, String name) {
        ExtendedObject object = new ExtendedObject(model);
        object.set("IDENT", new StringBuilder(cmodelname).
                append("_").
                append(name).toString());
        object.set("NAME", name);
        object.set("CMODEL", cmodelname);
        object.set("INDEX", cmodelitem.index);
        if (cmodelitem.model != null) {
            object.set("MODEL", cmodelitem.model.getName());
            object.set("MODEL_TYPE", 0);
            object.set("KEY_DIGITS", cmodelitem.keydigits);
            object.set("KEY_FORMAT", cmodelitem.keyformat);
        } else {
            object.set("MODEL", cmodelitem.cmodel.getName());
            object.set("MODEL_TYPE", 1);
        }
        return object;
    }
    
    private final String getColumnReferenceName(
            String tableref, UpdateData data) {
        return new StringBuilder("fk_").
                append(data.tablename).append("_").
                append(data.fieldname).append("_").
                append(tableref).toString();
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
        Documents documents;
        
        documents = getFunction();
        getmodel = documents.get("get_document_model");
        if (query.getJoins().size() == 0) {
            model = getmodel.run(connection, documents, query.getModel());
            return getExtendedObjectFrom(model, line);
        }

        model = new DocumentModel(null);
        for (String column : query.getColumns()) {
            composed = column.split("\\.");
            if (model.contains(composed[1]))
                throw new IocasteException(
                        new StringBuilder("model has already has item ").
                        append(composed[1]).toString());
            
            item = new DocumentModelItem(composed[1]);
            itemref = ((DocumentModel)getmodel.run(
                    connection, documents, composed[0])).
                    getModelItem(composed[1]);
            item.setTableFieldName(itemref.getTableFieldName());
            item.setIndex(itemref.getIndex());
            item.setDataElement(itemref.getDataElement());
            item.setDocumentModel(model);
            model.add(item);
        }
        
        return getExtendedObjectFrom(model, line);
    }
    
    private final DocumentModelItem getModelItem(Connection connection,
            Documents documents, String refname, String name) throws Exception {
        DocumentModel model;
        GetDocumentModel getmodel = documents.get("get_document_model");
        
        model = getmodel.run(connection, documents, refname);
        return (model == null)? null : model.getModelItem(name);
    }
    
    protected final String getModelItemIndex(Connection connection,
            Documents documents, DocumentModelItem item) throws Exception {
        GetDocumentModel modelget;
        DocumentModel model;
        String index = item.getIndex();
        
        if (index != null)
            return index;
        
        modelget = documents.get("get_document_model");
        model = modelget.run(
                connection, documents, item.getDocumentModel().getName());
        return model.getModelItem(item.getName()).getIndex();
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
    
    protected final int insertModelItem(Connection connection,
            Documents documents, DocumentModelItem item) throws Exception {
        DocumentModelItem reference;
        DataElement dataelement;
        String itemref, tname;
        DocumentModel model = item.getDocumentModel();
        
        dataelement = item.getDataElement();
        tname = getModelItemIndex(connection, documents,item);
        reference = item.getReference();
        if (reference != null) {
            itemref = getModelItemIndex(connection, documents, reference);
            if (model.getName().equals(reference.getDocumentModel().getName()))
               throw new IocasteException("Self model reference for %s", tname);
        } else {
            itemref = null;
        }
        
        if (update(connection, QUERIES[INS_ITEM], tname,
                model.getName(),
                item.getName(),
                item.getTableFieldName(),
                dataelement.getName(),
                item.getAttributeName(),
                itemref) == 0)
            return 0;
        
        if ((itemref != null) &&
                (update(connection, QUERIES[INS_FOREIGN], tname, itemref) == 0))
            return 0;
        return 1;
    }
    
    private void prepare(DataElement element) throws Exception {
        
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
            
        default:
            if (element.getLength() == 0)
                throw new IocasteException(
                        new StringBuilder("Invalid length for data element ").
                        append(element.getName()).toString());
            break;
        }
    }

    protected void prepareElements(Connection connection, Documents documents,
            DocumentModel model) throws Exception {
        DocumentModelItem reference;
        DataElement element;
        GetDataElement getde;
        String refname, modelrefname;
        
        getde = documents.get("get_data_element");
        for (DocumentModelItem item : model.getItens()) {
            if (item.isDummy())
                throw new IocasteException(
                        "Dummy items allowed only for references.");
            
            element = item.getDataElement();
            if (element == null) {
                reference = item.getReference();
                if (reference == null)
                    throw new IocasteException(
                            new StringBuilder(item.getName()).
                            append(" has an undefined element or reference.").
                            toString());
                
                if (reference.isDummy()) {
                    modelrefname = reference.getDocumentModel().getName();
                    refname = reference.getName();
                    reference = getModelItem(
                            connection, documents, modelrefname, refname);
                    
                    if (reference == null)
                        throw new IocasteException(
                                new StringBuilder(modelrefname).
                                append(" model used as reference for ").
                                append(refname).
                                append(" is undefined.").toString());
                }
                
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
                prepare(element);
            }   
        }
    }
    
    protected final void removeTableColumnReference(
            UpdateData data, DocumentModelItem reference) {
        String constraint, tableref;
        
        tableref = reference.getDocumentModel().getTableName();
        constraint = getColumnReferenceName(tableref, data);
        data.table.dropreference(constraint);
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
