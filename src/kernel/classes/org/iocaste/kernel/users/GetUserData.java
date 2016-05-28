package org.iocaste.kernel.users;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.kernel.database.Select;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class GetUserData extends AbstractHandler {
    private static final String QUERY =
            "select UNAME, SECRT, FNAME, SNAME, INIT from USERS001 "
            + "where UNAME = ?";

    @Override
    public Object run(Message message) throws Exception {
        String username = message.getst("username");
        User user = run(username);
        
        user.setSecret(null);
        return user;
    }
    
    @SuppressWarnings("unchecked")
    public User run(String username) throws Exception {
        User user;
        Connection connection;
        Object[] lines;
        Map<String, Object> columns;
        Select select;
        Users users = getFunction();
        
        connection = users.database.instance();
        select = users.database.get("select");
        lines = select.run(connection, QUERY, 1, username.toUpperCase());
        connection.close();
        if (lines == null)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        user = new User();
        user.setUsername((String)columns.get("UNAME"));
        user.setFirstname((String)columns.get("FNAME"));
        user.setSurname((String)columns.get("SNAME"));
        user.setSecret((String)columns.get("SECRT"));
        user.setInitialSecret((boolean)columns.get("INIT"));
        
        return user;
    }

}
