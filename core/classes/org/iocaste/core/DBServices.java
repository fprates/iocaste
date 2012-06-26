package org.iocaste.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hsqldb.HsqlException;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class DBServices {
    Config config;
    
    public DBServices() {
        config = new Config();
    }
    
    /**
     * 
     * @param connection
     * @throws SQLException 
     */
    public final void commit(Connection connection) throws SQLException {
        connection.commit();
    }
    
    public final void config(Properties properties) throws Exception {
        String driver = properties.getProperty("dbdriver");
        
        config.url = properties.getProperty("url");
        config.username = properties.getProperty("username");
        config.secret = properties.getProperty("secret");
        
        Class.forName(driver);
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
                    config.url, config.username, config.secret);;
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_UNCOMMITTED);
            
            return connection;
        
        } catch (HsqlException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLServerException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    /**
     * 
     * @param connection
     * @throws SQLException
     */
    public final void rollback(Connection connection) throws SQLException {
        connection.rollback();
    }
    
    /**
     * 
     * @param connection
     * @param query
     * @param rows
     * @param criteria
     * @return
     * @throws Exception
     */
    public final Object[] select(Connection connection, String query,
            int rows, Object... criteria) throws Exception {
        List<Map<String, Object>> lines;
        Map<String, Object> line;
        ResultSetMetaData metadata;
        PreparedStatement ps;
        ResultSet results;
        int cols = 1;
        
        System.err.println(query);
        
        try {
            ps = connection.prepareStatement(query);
            if (criteria != null)
                for (Object object : criteria)
                    ps.setObject(cols++, object);
            
            if (rows > 0)
                ps.setFetchSize(rows);
            
            results = ps.executeQuery();
            metadata = results.getMetaData();
            cols = metadata.getColumnCount();
        } catch (HsqlException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLServerException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLDataException e) {
            throw new SQLDataException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException (e.getMessage());
        }
        
        lines = new ArrayList<Map<String, Object>>();
        while (results.next()) {
            line = new HashMap<String, Object>();
            
            for (int i = 1; i <= cols; i++)
                line.put(metadata.getColumnName(i), results.getObject(i));
            
            lines.add(line);
        }
        
        results.close();
        
        return (lines.size() == 0)? null : lines.toArray();
    }
    
    /**
     * 
     * @param connection
     * @param query
     * @throws Exception
     */
    public final int update(Connection connection, String query,
            Object... criteria) throws Exception {
        PreparedStatement ps = null;
        int i = 1;
        
        System.err.println(query);
        try {
            ps = connection.prepareStatement(query);
            
            if (criteria != null)
                for (Object object : criteria)
                    ps.setObject(i++, object);
        
            return ps.executeUpdate();
        } catch (HsqlException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLServerException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLDataException e) {
            throw new SQLDataException(e.getMessage());
        } catch (SQLSyntaxErrorException e) {
            throw e;
        } catch (SQLException e) {
            return 0;
        } finally {
            ps.close();
        }
    }
}

class Config {
    public String url = null;
    public String username = null;
    public String secret = null;
}