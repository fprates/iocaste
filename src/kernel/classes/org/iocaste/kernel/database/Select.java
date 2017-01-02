package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.iocaste.protocol.Message;

public class Select extends AbstractDatabaseHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        Database database = getFunction();
        String query = message.getst("query");
        Object[] criteria = message.get("criteria");
        int rows = message.geti("rows");
        Connection connection = database.
                getDBConnection(message.getSessionid());
        
        return run(connection, query, rows, criteria);
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
    public final Object[] run(Connection connection, String query,
            int rows, Object... criteria) throws Exception {
        Exception ex;
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
        } catch (Exception e) {
            ex = new Exception(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } finally {
            ps.close();
        }
    }

}
