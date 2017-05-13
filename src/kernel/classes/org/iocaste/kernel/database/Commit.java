package org.iocaste.kernel.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class Commit extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        
        run(sessionid);
        return null;
    }
    
    /**
     * 
     * @param connection
     * @throws SQLException 
     */
    public final void run(String sessionid) throws Exception {
        Database database = getFunction();
        Connection connection = database.getDBConnection(sessionid);
        
        if (connection == null)
            return;
        
        connection.commit();
        database.freeConnection(sessionid);
    }

}
