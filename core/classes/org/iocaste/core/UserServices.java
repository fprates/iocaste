package org.iocaste.core;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.user.User;

public class UserServices {
    private static final byte INS_USER = 0;
    private static final byte USER_ID = 1;
    private static final byte UPD_USRID = 2;
    private static final byte UPD_USER = 3;
    private static final byte USER = 4;
    private static final String[] QUERIES = {
        "insert into USERS001(uname, secrt, usrid) values(?, ?, ?)",
        "select CRRNT from USERS000",
        "update USERS000 set CRRNT = ?",
        "update USERS001 set SECRT = ? where UNAME = ?",
        "select * from USERS001 where UNAME = ?"
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
    
    public static final Map<String, Object> getUserInfo(String username,
            Connection connection, DBServices db,
            Map<String, UserContext> sessions) throws Exception {
        User user;
        Map<String, Object> info, session;
        UserContext context;
        List<Map<String, Object>> connsessions;
        Object[] objects = db.select(connection, QUERIES[USER], 1, username);
        
        if (objects == null)
            return null;
        
        connsessions = new ArrayList<Map<String, Object>>();
        for (String sessionid : sessions.keySet()) {
            context = sessions.get(sessionid);
            user = context.getUser();
            if ((user == null) || (!user.getUsername().equals(username)))
                continue;
            
            session = new HashMap<String, Object>();
            session.put("terminal", context.getTerminal()); 
            session.put("connection.time", context.getConnTime());
            connsessions.add(session);
            break;
        }
        
        info = new HashMap<String, Object>();
        info.put("sessions", connsessions.toArray());
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
        
        db.update(connection, QUERIES[UPD_USER], secret, username);
    }
}
