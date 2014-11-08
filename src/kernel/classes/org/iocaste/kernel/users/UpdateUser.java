package org.iocaste.kernel.users;

import java.sql.Connection;
import java.util.List;

import org.iocaste.kernel.database.Update;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class UpdateUser extends AbstractHandler {
    private static final byte UPD_USER = 0;
    private static final String[] QUERIES = {
        "update USERS001 set SECRT = ?, INIT = ?, FNAME = ?, SNAME = ? " +
                "where UNAME = ?"
    };

    @Override
    public Object run(Message message) throws Exception {
        User user = message.get("user");
        String sessionid = message.getSessionid();
        
        run(user, sessionid);
        return null;
    }
    
    public void run(User user, String sessionid) throws Exception {
        List<String> sids;
        String username = user.getUsername();
        String secret = user.getSecret();
        boolean init = user.isInitialSecret();
        String firstname = user.getFirstname();
        String surname = user.getSurname();
        Users users = getFunction();
        Update update = users.database.get("update");
        Connection connection = users.database.getDBConnection(sessionid);
        
        update.run(connection, QUERIES[UPD_USER],
                secret, init, firstname, surname, username);
        
        sids = users.session.usersessions.get(user.getUsername());
        if (sids == null)
            return;
        
        for (String sid : sids)
            users.session.sessions.get(sid).setAuthorizations(null);
    }

}
