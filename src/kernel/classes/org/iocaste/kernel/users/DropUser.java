package org.iocaste.kernel.users;

import java.sql.Connection;

import org.iocaste.kernel.database.Update;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class DropUser extends AbstractHandler {
    private static final byte DEL_USER = 0;
    private static final byte DEL_USER_AUTH = 1;
    private static final String[] QUERIES = {
        "delete from USERS001 where UNAME = ?",
        "delete from USERS002 where UNAME = ?"
    };

    @Override
    public Object run(Message message) throws Exception {
        Users users = getFunction();
        String sessionid = message.getSessionid();
        String username = message.getst("username");
        Update update = users.database.get("update");
        Connection connection = users.database.getDBConnection(sessionid);
        
        update.run(connection, QUERIES[DEL_USER], username);
        update.run(connection, QUERIES[DEL_USER_AUTH], username);
        return null;
    }

}
