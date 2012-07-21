package org.iocaste.core;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map;

import org.iocaste.protocol.user.User;

public class UserServices {
    private static final byte INS_USER = 0;
    private static final byte USER_ID = 1;
    private static final byte UPD_USRID = 2;
    private static final byte UPD_USER = 3;
    private static final String[] QUERIES = {
        "insert into USERS001(uname, secrt, usrid) values(?, ?, ?)",
        "select CRRNT from USERS000",
        "update USERS000 set CRRNT = ?",
        "update USERS001 set SECRT = ? where uname = ?"
    };
    
    @SuppressWarnings("unchecked")
    public static final void add(User user, Connection connection,
            DBServices db) throws Exception {
        int userid;
        Map<String, Object> line;
        String username = user.getUsername();
        String secret = user.getSecret();
        Object[] objects = db.select(connection, QUERIES[USER_ID], 1);
        
        if (objects == null) {
            userid = 0;
        } else {
            line = (Map<String, Object>)objects[0];
            userid = ((BigDecimal)line.get("CRRNT")).intValue();
        }
        
        userid++;
        db.update(connection, QUERIES[INS_USER], username, secret, userid);
        db.update(connection, QUERIES[UPD_USRID], userid);
    }
    
    /**
     * 
     * @param user
     * @param connection
     * @param db
     * @throws Exception
     */
    public static final void update(User user, Connection connection,
            DBServices db) throws Exception {
        String username = user.getUsername();
        String secret = user.getSecret();
        
        db.update(connection, QUERIES[UPD_USER], secret, username);
    }
}
