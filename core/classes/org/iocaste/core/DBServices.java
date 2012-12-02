package org.iocaste.core;

import java.sql.CallableStatement;
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
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class DBServices {
    Config config;
    
    public DBServices() {
        config = new Config();
    }
    
    /**
     * 
     * @param connection
     * @param sql
     * @param in
     * @param out
     * @return
     * @throws Exception
     */
    public final Object callProcedure(Connection connection, String sql,
            Map<String, Object> in, Map<String, Integer> out)
                    throws Exception {
        int i;
        ResultSet rs;
        CallableStatement cs = connection.prepareCall(sql);
        
        try {
            if (in != null) {
                i = 1;
                for (String name : in.keySet())
                    cs.setObject(i++, in.get(name));
            }
                
            if (out != null) {
                i = 1;
                for (String name : out.keySet())
                    cs.registerOutParameter(i++, out.get(name));
            }
            
            if (cs.execute()) {
                rs = cs.getResultSet();
                return processResultSet(rs);
            } else {
                return cs.getUpdateCount();
            }
        } catch (HsqlException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLServerException e) {
            throw new SQLException(e.getMessage());
        } catch (MySQLSyntaxErrorException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLDataException e) {
            throw new SQLDataException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException (e.getMessage());
        } finally {
            cs.close();
        }
    }
    
    /**
     * 
     * @param connection
     * @throws SQLException 
     */
    public final void commit(Connection connection)
            throws SQLException {
        connection.commit();
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
        } catch (MySQLSyntaxErrorException e) {
            throw new SQLException(e.getMessage());
        } catch (MySQLNonTransientConnectionException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    private final Object[] processResultSet(ResultSet results)
            throws Exception {
        Map<String, Object> line;
        ResultSetMetaData metadata = results.getMetaData();
        int cols = metadata.getColumnCount();
        List<Map<String, Object>> lines = new ArrayList<Map<String, Object>>();
        
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
     * @throws SQLException
     */
    public final void rollback(Connection connection)
            throws SQLException {
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
        ResultSet results;
        int cols = 1;
        PreparedStatement ps = connection.prepareStatement(query);
        
        try {
            if (criteria != null)
                for (Object object : criteria)
                    ps.setObject(cols++, object);
            
            if (rows > 0)
                ps.setFetchSize(rows);
            
            results = ps.executeQuery();
            return processResultSet(results);
        } catch (HsqlException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLServerException e) {
            throw new SQLException(e.getMessage());
        } catch (MySQLSyntaxErrorException e) {
            throw new SQLException(e.getMessage());
        } catch (MySQLNonTransientConnectionException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLDataException e) {
            throw new SQLDataException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException (e.getMessage());
        } finally {
            ps.close();
        }
    }
    
    /**
     * 
     * @param connection
     * @param query
     * @throws Exception
     */
    public final int update(Connection connection, String query,
            Object... criteria) throws Exception {
        int i = 1;
        PreparedStatement ps = connection.prepareStatement(query);
        
        try {
            if (criteria != null)
                for (Object object : criteria)
                    ps.setObject(i++, object);
            
            return ps.executeUpdate();
        } catch (HsqlException e) {
            throw new SQLException(e.getMessage());
        } catch (SQLServerException e) {
            i = e.getErrorCode();
            switch (i) {
            case 2627: // constraint error code
                return 0;
            }
            throw new SQLException(e.getMessage());
        } catch (MySQLSyntaxErrorException e) {
            throw new SQLException(e.getMessage());
        } catch (MySQLNonTransientConnectionException e) {
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