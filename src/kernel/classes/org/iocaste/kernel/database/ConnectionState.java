package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.Map;

import org.iocaste.protocol.database.ConnectionInfo;

public class ConnectionState extends ConnectionInfo {
    private static final long serialVersionUID = 7448180548005159738L;
    public Connection connection;
    
    public ConnectionState(Map<Integer, ConnectionState> pool,
            DBConfig dbconfig) throws Exception {
        connection = DriverManager.getConnection(
                dbconfig.url, dbconfig.username, dbconfig.secret);
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(
                Connection.TRANSACTION_READ_COMMITTED);
        createdon = new Date();
        pool.put(connid = pool.size(), this);
    }
}
