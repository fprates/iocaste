package org.iocaste.install.dictionary;

import java.util.List;

import org.iocaste.documents.common.DataType;
import org.iocaste.kernel.common.Table;

public class Core extends Module {

    public Core(byte dbtype) {
        super(dbtype);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install(
     *    org.iocaste.install.dictionary.ModuleContext)
     */
    @Override
    public final List<String> install(ModuleContext context) {
        Table users000, users001, users002, auth001, auth002, auth003, auth004;
        
        users000 = tableInstance("USERS000");
        users000.add("CRRNT", DataType.NUMC, 5);
        
        users001 = tableInstance("USERS001");
        users001.key("UNAME", DataType.CHAR, 12);
        users001.add("SECRT", DataType.CHAR, 12);
        users001.add("FNAME", DataType.CHAR, 64);
        users001.add("SNAME", DataType.CHAR, 64);
        users001.add("USRID", DataType.NUMC, 5);
        users001.add("INIT", DataType.BOOLEAN, 1);

        auth001 = tableInstance("AUTH001");
        auth001.key("AUTNM", DataType.CHAR, 24);
        auth001.add("OBJCT", DataType.CHAR, 12);
        auth001.add("ACTIO", DataType.CHAR, 12);
        auth001.add("AUTID", DataType.NUMC, 5);

        auth002 = tableInstance("AUTH002");
        auth002.key("IDENT", DataType.NUMC, 8);
        auth002.ref("AUTNM", DataType.CHAR, 24, "AUTH001", "AUTNM");
        auth002.add("PARAM", DataType.CHAR, 12);
        auth002.add("VALUE", DataType.CHAR, 64);

        auth003 = tableInstance("AUTH003");
        auth003.key("PRFNM", DataType.CHAR, 12);
        auth003.add("PRFID", DataType.NUMC, 5);
        auth003.add("CRRNT", DataType.NUMC, 8);

        auth004 = tableInstance("AUTH004");
        auth004.key("IDENT", DataType.NUMC, 8);
        auth004.ref("PRFNM", DataType.CHAR, 12, "AUTH003", "PRFNM");
        auth004.ref("AUTNM", DataType.CHAR, 24, "AUTH001", "AUTNM");
        auth004.add("OBJCT", DataType.CHAR, 12);
        auth004.add("ACTIO", DataType.CHAR, 12);

        users002 = tableInstance("USERS002");
        users002.key("IDENT", DataType.NUMC, 8);
        users002.ref("UNAME", DataType.CHAR, 12, "USERS001", "UNAME");
        users002.ref("PRFNM", DataType.CHAR, 12, "AUTH003", "PRFNM");

        users000.set("crrnt", 1);
        insert(users000);
        
        users001.set("uname", "ADMIN");
        users001.set("secrt", "iocaste");
        users001.set("fname", "Administrator");
        users001.set("sname", "");
        users001.set("usrid", 1);
        users001.set("init", true);
        insert(users001);
        
        insertExecuteAuthorization(auth001, auth002, "PACKAGE.EXECUTE",
                "iocaste-packagetool");
        insertExecuteAuthorization(auth001, auth002, "CALENDAR.EXECUTE",
                "iocaste-calendar");
        insertExecuteAuthorization(auth001, auth002, "SH.EXECUTE",
                "iocaste-search-help");
        insertExecuteAuthorization(auth001, auth002, "HELP.EXECUTE",
                "iocaste-help");
        insertExecuteAuthorization(auth001, auth002, "EXHANDLER.EXECUTE",
                "iocaste-exhandler");

        insertAuthorizationProfile("ALL");
        linkAuthorizationToProfile("ALL", "PACKAGE.EXECUTE");
        compileAuthorizationProfile(auth003, auth004, "ALL");

        insertAuthorizationProfile("BASE");
        linkAuthorizationToProfile("BASE", "SH.EXECUTE");
        linkAuthorizationToProfile("BASE", "HELP.EXECUTE");
        linkAuthorizationToProfile("BASE", "EXHANDLER.EXECUTE");
        linkAuthorizationToProfile("BASE", "CALENDAR.EXECUTE");
        compileAuthorizationProfile(auth003, auth004, "BASE");

        linkUserToProfile(users002, "ADMIN", 101, "ALL");
        linkUserToProfile(users002, "ADMIN", 102, "BASE");
        
        return compile();
    }
}
