package org.iocaste.kernel.users;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.kernel.database.Select;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class GetUserData extends AbstractHandler {

    @SuppressWarnings("unchecked")
    public final String getSecret(Connection connection, User user)
            throws Exception {
        Object[] lines;
        Users users = getFunction();
        Select select = users.database.get("select");
        
        lines = select.run(connection,
                Users.QUERIES[Users.SECRET_QUERY], 1, user.getUsername());
        return (lines == null)? null : (String)
                ((Map<String, Object>)lines[0]).get("SECRT");
    }
    
    @Override
    public Object run(Message message) throws Exception {
        String username = message.getst("username");
        Connection connection;
        Users users = getFunction();
        connection = users.database.instance();
        return run(users, connection, username);
    }
    
    @SuppressWarnings("unchecked")
    public User run(Users users, Connection connection, String username)
            throws Exception {
        User user;
        Object[] lines;
        Map<String, Object> columns;
        Select select;
        
        select = users.database.get("select");
        lines = select.run(connection,
                Users.QUERIES[Users.USER_GET], 1, username.toUpperCase());
        if (lines == null)
            return null;
        
        columns = (Map<String, Object>)lines[0];
        user = new User();
        user.setUsername((String)columns.get("UNAME"));
        user.setFirstname((String)columns.get("FNAME"));
        user.setSurname((String)columns.get("SNAME"));
        
        return user;
    }

}
