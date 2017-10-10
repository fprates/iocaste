package org.iocaste.kernel.users;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.database.Select;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class IsInitialSecret extends AbstractHandler {

    @Override
    @SuppressWarnings("unchecked")
    public Object run(Message message) throws Exception {
        Object[] objects;
        String sessionid = message.getSessionid();
        Users users = getFunction();
        Select select = users.database.get("select");
        UserContext context = users.session.sessions.get(sessionid).usercontext;
        String username = context.getUser().getUsername();
        Connection connection = users.database.getDBConnection(sessionid);
        
        objects = select.run(connection,
                Users.QUERIES[Users.INIT_QUERY], 1, username);
        return (boolean)((Map<String, Object>)objects[0]).get("INIT");
    }

}
