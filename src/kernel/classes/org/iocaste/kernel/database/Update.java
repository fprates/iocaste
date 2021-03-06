package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class Update extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Database database = getFunction();
        String query = message.getst("query");
        Object[] criteria = message.get("criteria");
        Connection connection = database.
                getDBConnection(message.getSessionid());
        
        return run(connection, query, criteria);
    }
    
    /**
     * 
     * @param connection
     * @param query
     * @throws Exception
     */
    public final int run(Connection connection, String query,
            Object... criteria) throws Exception {
        Database database = getFunction();
        int i = 1;
        PreparedStatement ps = connection.prepareStatement(query);
        
        try {
            if (criteria != null)
                for (Object object : criteria)
                    ps.setObject(i++, object);
                    
            return ps.executeUpdate();
        } catch (SQLServerException e) {
            i = e.getErrorCode();
            switch (i) {
            case 547:
            case 3701: // drop table error
            case 2627: // constraint error code
                throw new SQLException(e.getMessage());
            }
            throw new SQLException(e.getMessage());
        } catch (SQLDataException e) {
            throw new SQLDataException(e.getMessage());
        } catch (SQLSyntaxErrorException e) {
            throw e;
        } catch (SQLException e) {
            switch (database.dbconfig.dbtype) {
            case "hsqldb":
                i = e.getErrorCode();
                switch (i) {
                case -177:
                    throw e;
                }
                break;
            case "mysql":
                i = e.getErrorCode();
                switch (i) {
                case 1062:
                    return 0;
                case 1205:
                    throw e;
                }
                break;
            }
            return -1;
        } finally {
            ps.close();
        }
    }

}
