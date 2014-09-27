package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.protocol.Message;

public class Commit extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        Connection connection;
        String sessionid = message.getSessionid();
        Database database = getFunction();
        UserContext context = database.session.sessions.get(sessionid);
        
        if (context == null)
            return null;
        
        connection = context.getConnection();
        if (connection == null)
            return null;
        
        run(connection);
        connection.close();
        context.setConnection(null);
        return null;
    }
    
    /**
     * 
     * @param connection
     * @throws SQLException 
     */
    public final void run(Connection connection) throws SQLException {
        connection.commit();
    }

}
