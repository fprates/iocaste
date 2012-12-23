package org.iocaste.install;

import java.util.LinkedHashMap;
import java.util.Map;

public class DBNames {
    public static final byte MYSQL = 0;
    public static final byte MSSQL = 1;
    public static final byte HSQLDB = 2;
    public static Map<String, Byte> names;
    
    static {
        names = new LinkedHashMap<>();
        names.put("mysql", MYSQL);
        names.put("mssql", MSSQL);
        names.put("hsqldb", HSQLDB);
    }
}
