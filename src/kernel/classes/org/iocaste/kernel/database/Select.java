package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;

import org.hsqldb.HsqlException;
import org.iocaste.kernel.common.Message;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class Select extends AbstractDatabaseHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        Database database = getFunction();
        String query = message.getString("query");
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

}
