package org.iocaste.kernel.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class DBNames {
    public static final byte MYSQL = 0;
    public static final byte MSSQL1 = 1;
    public static final byte HSQLDB = 2;
    public static final byte POSTGRES = 3;
    public static final byte MSSQL2 = 4;
    public static final byte LAST = MSSQL2;
    public static Map<String, Byte> names;
    public static final String[] DRIVERS = {
        "com.mysql.jdbc.Driver",
        "com.microsoft.sqlserver.jdbc.SQLServerDriver",
        "org.hsqldb.jdbcDriver",
        "org.postgresql.Driver",
        "com.microsoft.sqlserver.jdbc.SQLServerDriver"
    };
    
    static {
        names = new LinkedHashMap<>();
        names.put("mysql", MYSQL);
        names.put("mssql1", MSSQL1);
        names.put("hsqldb", HSQLDB);
        names.put("mssql2", MSSQL2);
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
