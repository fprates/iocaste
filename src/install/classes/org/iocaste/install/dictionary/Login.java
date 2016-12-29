package org.iocaste.install.dictionary;

import java.util.List;

import org.iocaste.documents.common.DataType;
import org.iocaste.kernel.common.Table;

public class Login extends Module {

    public Login(byte dbtype) {
        super(dbtype);
    }
    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install(
     *    org.iocaste.install.dictionary.ModuleContext)
     */
    @Override
    public final List<String> install(ModuleContext context) {
        Table docs001, docs002, docs003, docs004, docs005, docs006;
        String username, authname, profilename;
        
        docs001 = getTable("DOCS001");
        docs002 = getTable("DOCS002");
        docs003 = getTable("DOCS003");
        docs004 = getTable("DOCS004");
        docs005 = getTable("DOCS005");
        docs006 = getTable("DOCS006");

        insertElement(docs003, "USER_CONFIG.CURRENT", 0, 5, 3, false);
        insertElement(docs003, "LOGIN.USERNAME", 0, 12, DataType.CHAR, true);
        insertElement(docs003, "LOGIN.SECRET", 0, 12, DataType.CHAR, false);
        insertElement(docs003, "LOGIN.FIRSTNAME", 0, 64, DataType.CHAR, false);
        insertElement(docs003, "LOGIN.SURNAME", 0, 64, DataType.CHAR, false);
        insertElement(docs003, "LOGIN.ID", 0, 5, DataType.NUMC, false);
        insertElement(docs003, "LOGIN.INIT", 0, 1, DataType.BOOLEAN, false);
        insertElement(docs003, "AUTHORIZATION.NAME", 0, 24, 0, true);
        insertElement(docs003, "AUTHORIZATION.OBJECT", 0, 12, 0, true);
        insertElement(docs003, "AUTHORIZATION.ACTION", 0, 12, 0, true);
        insertElement(docs003, "AUTHORIZATION.INDEX", 0, 5, 3, false);
        insertElement(docs003, "AUTHORIZATION_ITEM.ID", 0, 8, 3, false);
        insertElement(docs003, "AUTHORIZATION_ITEM.NAME", 0, 12, 0, true);
        insertElement(docs003, "AUTHORIZATION_ITEM.VALUE", 0, 64, 0, true);
        insertElement(docs003, "USER_PROFILE.NAME", 0, 12, 0, true);
        insertElement(docs003, "USER_PROFILE.ID", 0, 5, 3, false);
        insertElement(docs003, "USER_PROFILE.CURRENT", 0, 8, 3, false);
        insertElement(docs003, "USER_PROFILE_ITEM.ID", 0, 8, 3, false);
        insertElement(docs003, "USER_AUTHORITY.ID", 0, 8, 3, false);
        
        insertModel(docs001, docs005,
                "USER_CONFIG", "USERS000", null);
        insertModelItem(docs001, docs002,
                "CURRENT", "CRRNT", "USER_CONFIG.CURRENT", null);
         
        insertModel(docs001, docs005,
                "LOGIN", "USERS001", null);
        username = insertModelKey(docs001, docs002, docs004,
                "USERNAME", "UNAME", "LOGIN.USERNAME", null);
        insertModelItem(docs001, docs002,
                "SECRET", "SECRT", "LOGIN.SECRET", null);
        insertModelItem(docs001, docs002,
                "FIRSTNAME", "FNAME", "LOGIN.FIRSTNAME", null);
        insertModelItem(docs001, docs002,
                "SURNAME", "SNAME", "LOGIN.SURNAME", null);
        insertModelItem(docs001, docs002,
                "ID", "USRID", "LOGIN.ID", null);
        insertModelItem(docs001, docs002,
                "INIT", "INIT", "LOGIN.INIT", null);
        
        insertModel(docs001, docs005,
                "AUTHORIZATION", "AUTH001", null);
        authname = insertModelKey(docs001, docs002, docs004,
                "NAME", "AUTNM", "AUTHORIZATION.NAME", null);
        insertModelItem(docs001, docs002,
                "OBJECT", "OBJCT", "AUTHORIZATION.OBJECT", null);
        insertModelItem(docs001, docs002,
                "ACTION", "ACTIO", "AUTHORIZATION.ACTION", null);
        insertModelItem(docs001, docs002,
                "INDEX", "AUTID", "AUTHORIZATION.INDEX", null);
    
        insertModel(docs001, docs005,
                "AUTHORIZATION_ITEM", "AUTH002", null);
        insertModelKey(docs001, docs002, docs004,
                "ID", "IDENT", "AUTHORIZATION_ITEM.ID", null);
        insertModelItem(docs001, docs002, docs006,
                "AUTHORIZATION", "AUTNM", "AUTHORIZATION.NAME", null, authname);
        insertModelItem(docs001, docs002,
                "NAME", "PARAM", "AUTHORIZATION_ITEM.NAME", null);
        insertModelItem(docs001, docs002,
                "VALUE", "VALUE", "AUTHORIZATION_ITEM.VALUE", null);
    
        insertModel(docs001, docs005,
                "USER_PROFILE", "AUTH003", null);
        profilename = insertModelKey(docs001, docs002, docs004,
                "NAME", "PRFNM", "USER_PROFILE.NAME", null);
        insertModelItem(docs001, docs002,
                "ID", "PRFID", "USER_PROFILE.ID", null);
        insertModelItem(docs001, docs002,
                "CURRENT", "CRRNT", "USER_PROFILE.CURRENT", null);
           
        insertModel(docs001, docs005,
                "USER_PROFILE_ITEM", "AUTH004", null);
        insertModelKey(docs001, docs002, docs004,
                "ID", "IDENT", "USER_PROFILE_ITEM.ID", null);
        insertModelItem(docs001, docs002, docs006,
                "PROFILE", "PRFNM", "USER_PROFILE.NAME", null, profilename);
        insertModelItem(docs001, docs002, docs006,
                "NAME", "AUTNM", "AUTHORIZATION.NAME", null, authname);
        insertModelItem(docs001, docs002,
                "OBJECT", "OBJCT", "AUTHORIZATION.OBJECT", null);
        insertModelItem(docs001, docs002,
                "ACTION", "ACTIO", "AUTHORIZATION.ACTION", null);
           
        insertModel(docs001, docs005,
                "USER_AUTHORITY", "USERS002", null);
        insertModelKey(docs001, docs002, docs004,
                "ID", "IDENT", "USER_AUTHORITY.ID", null);
        insertModelItem(docs001, docs002, docs006,
                "USERNAME", "UNAME", "LOGIN.USERNAME", null, username);
        insertModelItem(docs001, docs002, docs006,
                "PROFILE", "PRFNM", "USER_PROFILE.NAME", null, profilename);

        return compile();
    }
}
