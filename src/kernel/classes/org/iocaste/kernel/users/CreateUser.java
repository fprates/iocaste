package org.iocaste.kernel.users;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.database.Select;
import org.iocaste.kernel.database.Update;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class CreateUser extends AbstractHandler {
    private static final byte INS_USER = 0;
    private static final byte USER_ID = 1;
    private static final byte UPD_USRID = 2;
    private static final String[] QUERIES = {
        "insert into USERS001(uname, secrt, fname, sname, init, usrid) " +
                "values(?, ?, ?, ?, ?, ?)",
        "select CRRNT from USERS000",
        "update USERS000 set CRRNT = ?"
    };
    
    @SuppressWarnings("unchecked")
    @Override
    public Object run(Message message) throws Exception {
        Users users;
        String sessionid;
        Connection connection;
        int userid;
        Map<String, Object> line;
        Select select;
        Update update;
        Object[] objects;
        User user = message.get("userdata");
        
        if (user.getUsername() == null || user.getSecret() == null)
            throw new IocasteException("Invalid username or password");
        
        users = getFunction();
        sessionid = message.getSessionid();
        connection = users.database.getDBConnection(sessionid);
        
        select = users.database.get("select");
        objects = select.run(connection, QUERIES[USER_ID], 1);
        
        if (objects == null) {
            userid = 0;
        } else {
            line = (Map<String, Object>)objects[0];
            userid = ((BigDecimal)line.get("CRRNT")).intValue();
        }
        
        userid++;
        update = users.database.get("update");
        update.run(connection, QUERIES[INS_USER], user.getUsername(),
                user.getSecret(),
                user.getFirstname(),
                user.getSurname(),
                user.isInitialSecret(),
                userid);
        update.run(connection, QUERIES[UPD_USRID], userid);
        return null;
    }
}
