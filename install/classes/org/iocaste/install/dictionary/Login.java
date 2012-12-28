package org.iocaste.install.dictionary;

import java.util.List;

import org.iocaste.documents.common.DataType;

public class Login extends Module {

    public Login(byte dbtype) {
        super(dbtype);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install()
     */
    @Override
    public final List<String> install() {
        Table docs001, docs002, docs003, docs004, docs005, docs006;
        
        docs001 = getTable("DOCS001");
        docs002 = getTable("DOCS002");
        docs003 = getTable("DOCS003");
        docs004 = getTable("DOCS004");
        docs005 = getTable("DOCS005");
        docs006 = getTable("DOCS006");
        
        insertModel(docs001, docs005, "USER_CONFIG", "USERS000", null);
        insertElement(docs003, "USER_CONFIG.CURRENT", 0, 5, 3, false);
        insertModelItem(docs002, "USER_CONFIG.CURRENT", "USER_CONFIG", "CRRNT",
                "USER_CONFIG.CURRENT", null);
         
        insertModel(docs001, docs005, "LOGIN", "USERS001", null);
        insertElement(docs003, "LOGIN.USERNAME", 0, 12, DataType.CHAR, true);
        insertElement(docs003, "LOGIN.SECRET", 0, 12, DataType.CHAR, false);
        insertElement(docs003, "LOGIN.ID", 0, 5, DataType.NUMC, false);
        insertElement(docs003, "LOGIN.FIRSTNAME", 0, 64, DataType.CHAR, false);
        insertElement(docs003, "LOGIN.SURNAME", 0, 64, DataType.CHAR, false);
        insertElement(docs003, "LOGIN.INIT", 0, 1, DataType.BOOLEAN, false);
        insertModelKey(docs002, docs004, "LOGIN.USERNAME", "LOGIN", "UNAME",
                "LOGIN.USERNAME", null);
        insertModelItem(docs002, "LOGIN.SECRET", "LOGIN", "SECRT",
                "LOGIN.SECRET", null);
        insertModelItem(docs002, "LOGIN.ID", "LOGIN", "USRID", "LOGIN.ID",
                null);
        insertModelItem(docs002, "LOGIN.FIRSTNAME", "LOGIN", "FNAME",
                "LOGIN.FIRSTNAME", null);
        insertModelItem(docs002, "LOGIN.SURNAME", "LOGIN", "SNAME",
                "LOGIN.SURNAME", null);
        insertModelItem(docs002, "LOGIN.INIT", "LOGIN", "INIT", "LOGIN.INIT",
                null);
        
        insertModel(docs001, docs005, "AUTHORIZATION", "AUTH001", null);
        insertElement(docs003, "AUTHORIZATION.NAME", 0, 24, 0, true);
        insertElement(docs003, "AUTHORIZATION.OBJECT", 0, 12, 0, true);
        insertElement(docs003, "AUTHORIZATION.ACTION", 0, 12, 0, true);
        insertElement(docs003, "AUTHORIZATION.INDEX", 0, 5, 3, false);
        insertModelKey(docs002, docs004, "AUTHORIZATION.NAME", "AUTHORIZATION",
                "AUTNM", "AUTHORIZATION.NAME", null);
        insertModelItem(docs002, "AUTHORIZATION.OBJECT", "AUTHORIZATION",
                "OBJCT", "AUTHORIZATION.OBJECT", null);
        insertModelItem(docs002, "AUTHORIZATION.ACTION", "AUTHORIZATION",
                "ACTIO", "AUTHORIZATION.ACTION", null);
        insertModelItem(docs002, "AUTHORIZATION.INDEX", "AUTHORIZATION",
                "AUTID", "AUTHORIZATION.INDEX", null);
    
        insertModel(docs001, docs005, "AUTHORIZATION_ITEM", "AUTH002", null);
        insertElement(docs003, "AUTHORIZATION_ITEM.ID", 0, 8, 3, false);
        insertElement(docs003, "AUTHORIZATION_ITEM.NAME", 0, 12, 0, true);
        insertElement(docs003, "AUTHORIZATION_ITEM.VALUE", 0, 64, 0, true);
        insertModelKey(docs002, docs004, "AUTHORIZATION_ITEM.ID",
                "AUTHORIZATION_ITEM", "IDENT", "AUTHORIZATION_ITEM.ID", null);
        insertModelItem(docs002, docs006, "AUTHORIZATION_ITEM.AUTHORIZATION",
                "AUTHORIZATION_ITEM", "AUTNM", "AUTHORIZATION.NAME", null,
                "AUTHORIZATION.NAME");
        insertModelItem(docs002, "AUTHORIZATION_ITEM.NAME",
                "AUTHORIZATION_ITEM", "PARAM", "AUTHORIZATION_ITEM.NAME", null);
        insertModelItem(docs002, "AUTHORIZATION_ITEM.VALUE",
                "AUTHORIZATION_ITEM", "VALUE", "AUTHORIZATION_ITEM.VALUE",
                null);
    
        insertModel(docs001, docs005, "USER_PROFILE", "AUTH003", null);
        insertElement(docs003, "USER_PROFILE.NAME", 0, 12, 0, true);
        insertElement(docs003, "USER_PROFILE.ID", 0, 5, 3, false);
        insertElement(docs003, "USER_PROFILE.CURRENT", 0, 8, 3, false);
        insertModelKey(docs002, docs004, "USER_PROFILE.NAME", "USER_PROFILE",
                "PRFNM", "USER_PROFILE.NAME", null);
        insertModelItem(docs002, "USER_PROFILE.ID", "USER_PROFILE", "PRFID",
                "USER_PROFILE.ID", null);
        insertModelItem(docs002, "USER_PROFILE.CURRENT", "USER_PROFILE",
                "CRRNT", "USER_PROFILE.CURRENT", null);
           
        insertModel(docs001, docs005, "USER_PROFILE_ITEM", "AUTH004", null);
        insertElement(docs003, "USER_PROFILE_ITEM.ID", 0, 8, 3, false);
        insertModelKey(docs002, docs004, "USER_PROFILE_ITEM.ID",
                "USER_PROFILE_ITEM", "IDENT", "USER_PROFILE_ITEM.ID", null);
        insertModelItem(docs002, docs006, "USER_PROFILE_ITEM.PROFILE",
                "USER_PROFILE_ITEM", "PRFNM", "USER_PROFILE.NAME", null,
                "USER_PROFILE.NAME");
        insertModelItem(docs002, docs006, "USER_PROFILE_ITEM.NAME",
                "USER_PROFILE_ITEM", "AUTNM", "AUTHORIZATION.NAME", null,
                "AUTHORIZATION.NAME");
        insertModelItem(docs002, "USER_PROFILE_ITEM.OBJECT", "USER_PROFILE_ITEM",
                "OBJCT", "AUTHORIZATION.OBJECT", null);
        insertModelItem(docs002, "USER_PROFILE_ITEM.ACTION", "USER_PROFILE_ITEM",
                "ACTIO", "AUTHORIZATION.ACTION", null);
           
        insertModel(docs001, docs005, "USER_AUTHORITY", "USERS002", null);
        insertElement(docs003, "USER_AUTHORITY.ID", 0, 8, 3, false);
        insertModelKey(docs002, docs004, "USER_AUTHORITY.ID", "USER_AUTHORITY",
                "IDENT", "USER_AUTHORITY.ID", null);
        insertModelItem(docs002, docs006, "USER_AUTHORITY.USERNAME",
                "USER_AUTHORITY", "UNAME", "LOGIN.USERNAME", null,
                "LOGIN.USERNAME");
        insertModelItem(docs002, docs006, "USER_AUTHORITY.PROFILE",
                "USER_AUTHORITY", "PRFNM", "USER_PROFILE.NAME", null,
                "USER_PROFILE.NAME");

        return compile();
    }
}
