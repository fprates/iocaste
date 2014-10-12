package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hsqldb.HsqlException;
import org.iocaste.kernel.common.AbstractFunction;
import org.iocaste.kernel.session.Session;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class Database extends AbstractFunction {
    private Map<String, Connection> connections;
    public Config config;
    public Session session;
    
    public Database() {
        config = new Config();
        connections = new HashMap<>();
        export("call_procedure", new CallProcedure());
        export("checked_select", new CheckedSelect());
        export("commit", new Commit());
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
        
        config.url = properties.getProperty("url");
        config.username = properties.getProperty("username");
        config.secret = properties.getProperty("secret");
        config.dbtype = properties.getProperty("dbtype");
        
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
                    config.url, config.username, config.secret);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_UNCOMMITTED);
            
            return connection;
        } catch (HsqlException e) {
            throw new SQLException(e.getMessage());
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

class Config {
    public String url;
    public String username;
    public String secret;
    public String dbtype;
}