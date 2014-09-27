package org.iocaste.kernel.session;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.database.Select;
import org.iocaste.kernel.users.Users;
import org.iocaste.protocol.Message;

public class GetSessionInfo extends AbstractHandler {
    private static final String QUERY =
            "select * from USERS001 where UNAME = ?";
    

    @Override
    public Object run(Message message) throws Exception {
        Users users = getFunction();
        String sessionid = message.getString("sessionid");
        Connection connection = users.database.
                getDBConnection(message.getSessionid());
        
        return getSessionInfo(
                users.session.sessions.get(sessionid), connection);
    }
    
    /**
     * 
     * @param usrctx
     * @param connection
     * @return
     * @throws Exception
     */
    private final Map<String, Object> getSessionInfo(UserContext usrctx,
            Connection connection) throws Exception {
        Users users;
        Map<String, Object> info;
        Object[] objects;
        String username;
        Select select;
        
        if (usrctx == null)
            return null;
        
        users = getFunction();
        username = usrctx.getUser().getUsername();
        select = users.database.get("select");
        objects = select.run(connection, QUERY, 1, username);
        if (objects == null)
            return null;
        
        info = new HashMap<>();
        info.put("username", username);
        info.put("terminal", usrctx.getTerminal()); 
        info.put("connection.time", usrctx.getConnTime());
        
        return info;
    }

}
