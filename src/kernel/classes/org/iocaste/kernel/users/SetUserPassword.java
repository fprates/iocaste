package org.iocaste.kernel.users;

import java.sql.Connection;

import org.iocaste.kernel.database.Update;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class SetUserPassword extends AbstractHandler {
    private static final String UPDATE_SECRET =
            "update USERS001 set SECRT = ?, INIT = ? where UNAME = ?";

    @Override
    public Object run(Message message) throws Exception {
        String username = message.getst("username");
        String secret = message.getst("secret");
        boolean initial = message.getbl("initial");
        String sessionid = message.getSessionid();
        Users users = getFunction();
        Update update = users.database.get("update");
        Connection connection = users.database.getDBConnection(sessionid);
        
        update.run(connection, UPDATE_SECRET, secret, initial, username);
        return null;
    }

}
