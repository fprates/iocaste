package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.protocol.Message;

import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;

public class Rollback extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        Connection connection;
        Database database = getFunction();
        String sessionid = message.getSessionid();
        UserContext context = database.session.sessions.get(sessionid);
        
        if (context == null && database.isAuthorizedCall())
            return null;
        
        connection = context.getConnection();
        if (connection == null)
            return null;
        
        run(connection);
        connection.close();
        context.setConnection(null);
        return null;
    }
    
    public void run(Connection connection) throws SQLException {
        try {
            connection.rollback();
        } catch (MySQLNonTransientConnectionException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
