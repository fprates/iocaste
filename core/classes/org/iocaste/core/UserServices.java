package org.iocaste.core;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.user.User;

public class UserServices {
    private static final byte INS_USER = 0;
    private static final byte USER_ID = 1;
    private static final byte UPD_USRID = 2;
    private static final byte UPD_USER = 3;
    private static final byte USER = 4;
    private static final String[] QUERIES = {
        "insert into USERS001(uname, secrt, fname, sname, init, usrid) " +
                "values(?, ?, ?, ?, ?, ?)",
        "select CRRNT from USERS000",
        "update USERS000 set CRRNT = ?",
        "update USERS001 set SECRT = ?, INIT = ?, FNAME = ?, SNAME = ? " +
                "where UNAME = ?",
        "select * from USERS001 where UNAME = ?"
    };
    
    @SuppressWarnings("unchecked")
    public static final void add(User user, Connection connection,
            DBServices db) throws Exception {
        int userid;
        Map<String, Object> line;
        Object[] objects = db.select(connection, QUERIES[USER_ID], 1);
        
        if (objects == null) {
            userid = 0;
        } else {
            line = (Map<String, Object>)objects[0];
            userid = ((BigDecimal)line.get("CRRNT")).intValue();
        }
        
        userid++;
        db.update(connection, QUERIES[INS_USER], user.getUsername(),
                user.getSecret(),
                user.getFirstname(),
                user.getSurname(),
                user.isInitialSecret(),
                userid);
        db.update(connection, QUERIES[UPD_USRID], userid);
    }
    
    public static final Map<String, Object> getSessionInfo(UserContext usrctx,
            Connection connection, DBServices db) throws Exception {
        Map<String, Object> info;
        Object[] objects;
        String username;
        
        if (usrctx == null)
            return null;
        
        username = usrctx.getUser().getUsername();
        objects = db.select(connection, QUERIES[USER], 1, username);
        if (objects == null)
            return null;
        
        info = new HashMap<String, Object>();
        info.put("username", username);
        info.put("terminal", usrctx.getTerminal()); 
        info.put("connection.time", usrctx.getConnTime());
        
        return info;
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
        boolean init = user.isInitialSecret();
        String firstname = user.getFirstname();
        String surname = user.getSurname();
        
        db.update(connection, QUERIES[UPD_USER],
                secret, init, firstname, surname, username);
    }
}
