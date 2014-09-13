package org.iocaste.kernel.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Map;

import org.hsqldb.HsqlException;
import org.iocaste.kernel.common.Message;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class CallProcedure extends AbstractDatabaseHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        int i;
        ResultSet rs;
        Database database = getFunction();
        String sql = message.getString("sql");
        Map<String, Object> in = message.get("in");
        Map<String, Integer> out = message.get("out");
        String sessionid = message.getSessionid();
        Connection connection = database.getDBConnection(sessionid);
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
}
