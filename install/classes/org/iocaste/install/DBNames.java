package org.iocaste.install;

import java.util.LinkedHashMap;
import java.util.Map;

public class DBNames {
    public static final byte MYSQL = 0;
    public static final byte MSSQL = 1;
    public static final byte HSQLDB = 2;
    public static final byte POSTGRES = 3;
    public static Map<String, Byte> names;
    public static final String[] DRIVERS = {
        "com.microsoft.sqlserver.jdbc.SQLServerDriver",
        "com.mysql.jdbc.Driver",
        "org.hsqldb.jdbcDriver",
        "org.postgresql.Driver"
    };
    
    static {
        names = new LinkedHashMap<>();
        names.put("mysql", MYSQL);
        names.put("mssql", MSSQL);
        names.put("hsqldb", HSQLDB);
        /*
         * on postgres, we must commit() or rollback() changes if a
         * SQLException is raised, while in iocaste a transaction continues
         * even so.
         * 
         * until we find a solution out, temporally disable postgresql.
         */
//        names.put("postgres", POSTGRES);
    }
}
