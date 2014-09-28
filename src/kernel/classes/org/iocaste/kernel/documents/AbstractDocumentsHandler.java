package org.iocaste.kernel.documents;

import org.iocaste.kernel.common.AbstractHandler;
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
    protected static final byte INS_ELEMENT = 1;
    
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
                "values(?, ?, ?, ?, ?, ?)"
    };
    
    @Override
    public abstract Object run(Message message) throws Exception;

}