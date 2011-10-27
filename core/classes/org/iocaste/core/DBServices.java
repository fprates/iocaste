package org.iocaste.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
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
            String query, Object[] criteria) throws Exception {
        Object[] regs;
        Map<String, Object> line;
        int cols;
        int size;
        ResultSetMetaData metadata;
        int k = 0;
        ResultSet results = null;
        
        try {
            results = connection.prepareStatement(query).executeQuery();
            size = results.getFetchSize();
            
            if (size == 0)
                return null;
            
            regs = new Object[size];
            results.beforeFirst();
            
            while (results.next()) {
                metadata = results.getMetaData();
                cols = metadata.getColumnCount();
                line = new HashMap<String, Object>();
                
                for (int i = 0; i < cols; i++)
                    line.put(metadata.getColumnName(i), results.getObject(i));
                
                regs[k++] = line;
            }
            
            return regs;
        } finally {
            results.close();
        }
    }
}
