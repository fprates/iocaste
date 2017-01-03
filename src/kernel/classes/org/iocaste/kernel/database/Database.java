package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.iocaste.kernel.config.Config;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractFunction;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class Database extends AbstractFunction {
    private Map<String, Connection> connections;
    public DBConfig dbconfig;
    public Config config;
    public Session session;
    
    public Database() {
        dbconfig = new DBConfig();
        connections = new HashMap<>();
        export("call_procedure", new CallProcedure());
        export("checked_select", new CheckedSelect());
        export("commit", new Commit());
        export("disconnected_operation", new DisconnectedOperation());
        export("ext_db_instance", new InstanceExternalDB());
        export("rollback", new Rollback());
        export("select", new Select());
        export("update", new Update());
    }
    
    /**
     * 
     * @param properties
     * @throws Exception
     */
    public final void config(Properties properties) throws Exception {
        String driver = properties.getProperty("dbdriver");
        
        dbconfig.url = properties.getProperty("url");
        dbconfig.username = properties.getProperty("username");
        dbconfig.secret = properties.getProperty("secret");
        dbconfig.dbtype = properties.getProperty("dbtype");
        
        Class.forName(driver);
    }
    
    /**
     * 
     * @param sessionid
     * @return
     * @throws Exception
     */
    public final Connection getDBConnection(String sessionid)
            throws Exception {
        Connection connection = connections.get(sessionid);
        
        if (connection == null) {
            connection = instance();
            connections.put(sessionid, connection);
        }
        
        return connection;
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public final Connection instance() throws Exception {
        Connection connection;
        
        try {
            connection = DriverManager.getConnection(
                    dbconfig.url, dbconfig.username, dbconfig.secret);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);
            
            return connection;
        } catch (SQLServerException e) {
            throw new SQLException(e.getMessage());
        } catch (MySQLSyntaxErrorException e) {
            throw new SQLException(e.getMessage());
        } catch (MySQLNonTransientConnectionException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public final void removeDBConnection(String sessionid) {
        connections.remove(sessionid);
    }
}

class DBConfig {
    public String url;
    public String username;
    public String secret;
    public String dbtype;
}
