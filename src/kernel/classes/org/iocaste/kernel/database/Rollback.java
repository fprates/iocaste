package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;

public class Rollback extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        
        run(sessionid);
        return null;
    }
    
    public void run(String sessionid) throws Exception {
        Connection connection;
        Database database = getFunction();
        
        connection = database.getDBConnection(sessionid);
        if (connection == null)
            return;
        
        try {
            connection.rollback();
        } catch (MySQLNonTransientConnectionException e) {
            throw new SQLException(e.getMessage());
        } finally {
            database.freeConnection(sessionid);
        }
    }

}
