package org.iocaste.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBServices {
    private DataSource ds;
    
    public DBServices() {
        Context initContext;
        try {
            initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/iocaste");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * @param connection
     * @throws SQLException 
     */
    public final void commit(Connection connection) throws SQLException {
        connection.commit();
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public final Connection instance() throws Exception {
        return ds.getConnection();
    }
    
    /**
     * 
     * @param connection
     * @param query
     * @param criteria
     * @return
     */
    public final Object[] select(Connection connection, 
            String query, Object... criteria) throws Exception {
        List<Map<String, Object>> lines;
        Map<String, Object> line;
        ResultSetMetaData metadata;
        PreparedStatement ps;
        ResultSet results;
        int cols = 1;
        
        System.err.println(query);
        
        ps = connection.prepareStatement(query);
        if (criteria != null)
            for (Object object : criteria)
                ps.setObject(cols++, object);
        
        results = ps.executeQuery();
        lines = new ArrayList<Map<String, Object>>();
        
        while (results.next()) {
            metadata = results.getMetaData();
            cols = metadata.getColumnCount();
            line = new HashMap<String, Object>();
            
            for (int i = 1; i <= cols; i++)
                line.put(metadata.getColumnName(i), results.getObject(i));
            
            lines.add(line);
        }
        
        results.close();
        
        return lines.toArray();
    }
    
    /**
     * 
     * @param connection
     * @param query
     * @throws Exception
     */
    public final int update(Connection connection, String query,
            Object... criteria) throws Exception {
        PreparedStatement ps = connection.prepareStatement(query);
        int i = 1;
        
        System.err.println(query);
        
        if (criteria != null)
            for (Object object : criteria)
                ps.setObject(i++, object);
        
        try {            
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        } finally {
            ps.close();
        }
    }
}
