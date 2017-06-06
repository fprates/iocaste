package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.iocaste.kernel.config.Config;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.database.ConnectionInfo;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class Database extends AbstractFunction {
    private Map<String, Integer> connections;
    private Map<Integer, ConnectionState> pool;
    public DBConfig dbconfig;
    public Config config;
    public Session session;
    
    public Database() {
        dbconfig = new DBConfig();
        connections = new HashMap<>();
        pool = new HashMap<>();
        export("call_procedure", new CallProcedure());
        export("checked_select", new CheckedSelect());
        export("commit", new Commit());
        export("connection_pool_info_get", new GetConnectionPoolInfo());
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
    
    private final void free(int connid) {
        pool.get(connid).assigned = false;
    }
    
    public final void free(ConnectionState connstate) {
        free(connstate.connid);
    }
    
    public final void freeConnection(String sessionid) {
        free(connections.get(sessionid));
        connections.remove(sessionid);
    }
    
    public final ConnectionInfo[] getConnections() {
        int i = 0;
        ConnectionInfo[] connections = new ConnectionInfo[pool.size()];
        for (int key : pool.keySet())
            connections[i++] = pool.get(key);
        return connections;
    }
    
    /**
     * 
     * @param sessionid
     * @return
     * @throws Exception
     */
    public final Connection getDBConnection(String sessionid)
            throws Exception {
        ConnectionState connstate = pool.get(connections.get(sessionid));
        
        if (connstate != null)
            return connstate.connection;
        
        connstate = instance();
        connstate.sessionid = sessionid;
        connections.put(sessionid, connstate.connid);
        return connstate.connection;
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public final ConnectionState instance() throws Exception {
        ConnectionState connstate;
        for (int key : pool.keySet()) {
            connstate = pool.get(key);
            if (connstate.assigned)
                continue;
            connstate.assigned = true;
            return connstate;
        }
        
        try {
            connstate = new ConnectionState(pool, dbconfig);
            connstate.assigned = true;
            return connstate;
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
}

class DBConfig {
    public String url;
    public String username;
    public String secret;
    public String dbtype;
}
