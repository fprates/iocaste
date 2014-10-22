package org.iocaste.kernel.documents;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.database.Select;
import org.iocaste.kernel.database.Update;
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
        "insert into DOCS001(docid, tname, class) values(?, ?, ?)",
        "insert into DOCS005(tname, docid) values(? , ?)",
        "update DOCS003 set decim = ?, lngth = ?, etype = ?, upcas = ? " +
                "where ename = ?",
        "update DOCS002 set docid = ?, nritm = ?, fname = ?, ename = ?, " +
                "attrb = ?, itref = ? where iname = ?",
        "insert into DOCS003(ename, decim, lngth, etype, upcas, atype) " +
                "values(?, ?, ?, ?, ?, ?)",
        "select CRRNT from RANGE001 where ident = ?",
        "update RANGE001 set crrnt = ? where ident = ?"
    };
    
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
        Object value;
        ExtendedObject object = new ExtendedObject(model);
        
        for (DocumentModelItem modelitem : model.getItens()) {
            value = line.get(modelitem.getTableFieldName());
            object.set(modelitem, value);
        }
        
        return object;
    }
    
    protected final DocumentModelItem getModelKey(DocumentModel model) {
        for (DocumentModelKey key : model.getKeys())
            return model.getModelItem(key.getModelItemName());
        return null;
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
    
    @Override
    public abstract Object run(Message message) throws Exception;
    
    protected Object[] select(Connection connection, String query, int rows,
            Object... criteria) throws Exception {
        Documents documents = getFunction();
        Select select = documents.database.get("select");
        return select.run(connection, query, rows, criteria);
    }
    
    protected final int update(Connection connection, String query,
            Object... criteria) throws Exception {
        Documents documents = getFunction();
        Update update = documents.database.get("update");
        return update.run(connection, query, criteria);
    }

}
