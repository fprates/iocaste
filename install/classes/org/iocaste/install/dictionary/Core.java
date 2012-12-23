package org.iocaste.install.dictionary;

import java.sql.Statement;

public class Core extends Module {

    public static final void install(byte sqldb, Statement ps)
            throws Exception {
        Table users000, users001, users002, auth001, auth002, auth003, auth004;
        
        users000 = tableInstance("USERS000");
        users000.add("CRRNT", NUMC, 5);
        
        users001 = tableInstance("USERS001");
        users001.key("UNAME", CHAR, 12);
        users001.add("SECRT", CHAR, 12);
        users001.add("FNAME", CHAR, 64);
        users001.add("SNAME", CHAR, 64);
        users001.add("USRID", NUMC, 5);

        auth001 = tableInstance("AUTH001");
        auth001.key("AUTNM", CHAR, 24);
        auth001.add("OBJCT", CHAR, 12);
        auth001.add("ACTIO", CHAR, 12);
        auth001.add("AUTID", NUMC, 5);

        auth002 = tableInstance("AUTH002");
        auth002.key("IDENT", NUMC, 8);
        auth002.ref("AUTNM", CHAR, 24, "AUTH001", "AUTNM");
        auth002.add("PARAM", CHAR, 12);
        auth002.add("VALUE", CHAR, 64);

        auth003 = tableInstance("AUTH003");
        auth003.key("PRFNM", CHAR, 12);
        auth003.add("PRFID", NUMC, 5);
        auth003.add("CRRNT", NUMC, 8);

        auth004 = tableInstance("AUTH004");
        auth004.key("IDENT", NUMC, 8);
        auth004.ref("PRFNM", CHAR, 12, "AUTH003", "PRFNM");
        auth004.ref("AUTNM", CHAR, 24, "AUTH001", "AUTNM");
        auth004.add("OBJCT", CHAR, 12);
        auth004.add("ACTIO", CHAR, 12);

        users002 = tableInstance("USERS002");
        users002.key("IDENT", NUMC, 8);
        users002.ref("UNAME", CHAR, 12, "USERS001", "UNAME");
        users002.ref("PRFNM", CHAR, 12, "AUTH003", "PRFNM");

        users000.set("crrnt", 1);
        insert(users000);
        
        users001.set("uname", "ADMIN");
        users001.set("secrt", "iocaste");
        users001.set("fname", "Administrator");
        users001.set("sname", "");
        users001.set("usrid", 1);
        insert(users001);
        
        insertExecuteAuthorization(auth001, auth002, "PACKAGE.EXECUTE",
                "iocaste-packagetool");
        insertExecuteAuthorization(auth001, auth002, "TASKSEL.EXECUTE",
                "iocaste-tasksel");
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
        linkAuthorizationToProfile("BASE", "TASKSEL.EXECUTE");
        linkAuthorizationToProfile("BASE", "SH.EXECUTE");
        linkAuthorizationToProfile("BASE", "HELP.EXECUTE");
        linkAuthorizationToProfile("BASE", "EXHANDLER.EXECUTE");
        compileAuthorizationProfile(auth003, auth004, "BASE");

//            insert into USERS002(ident, uname, prfnm) values(101, 'ADMIN', 'ALL');
//            insert into USERS002(ident, uname, prfnm) values(102, 'ADMIN', 'BASE');
        compile(ps);
    }
}
