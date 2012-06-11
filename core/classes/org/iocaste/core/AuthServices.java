package org.iocaste.core;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.user.Authorization;

public class AuthServices {
    private static final byte USER_AUTHORITY = 0;
    private static final byte USER_PROFILE_ITEM = 1;
    private static final byte AUTHORIZATION_ITEM = 2;
    private static final String[] QUERIES = {
        "select * from USERS002 where UNAME = ?",
        "select * from AUTH004 where PRFNM = ? and OBJCT = ? and ACTIO = ?",
        "select * from AUTH002 where AUTNM = ?"
    };
    private static Map<String, Authorization[]> authorizations =
            new HashMap<String, Authorization[]>();
            
    /**
     * 
     * @param connection
     * @param db
     * @param username
     * @param authname
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final Authorization[] getAuthorization(Connection connection,
            DBServices db, String username, String object, String action)
                    throws Exception {
        Authorization authorization;
        Map<String, Object> resultmap;
        List<Authorization> authlist;
        String profilename, authname, name, value;
        Authorization[] autharray;
        
        if (authorizations.containsKey(username))
            return authorizations.get(username);
        
        Object[] parameters, profileitens, profiles =
                db.select(connection, QUERIES[USER_AUTHORITY], 0, username);
        
        if (profiles == null)
            return null;
        
        authlist = new ArrayList<Authorization>();
        
        for (Object profile : profiles) {
            resultmap = (Map<String, Object>)profile;
            profilename = (String)resultmap.get("PRFNM");
            profileitens = db.select(connection, QUERIES[USER_PROFILE_ITEM],
                    0, profilename, object, action);
            
            if (profileitens == null)
                continue;
            
            for (Object profileitem : profileitens) {
                resultmap = (Map<String, Object>)profileitem;
                
                authname = (String)resultmap.get("AUTNM");
                parameters = db.select(connection, QUERIES[AUTHORIZATION_ITEM],
                        0, authname);
                
                authorization = new Authorization(authname);
                authorization.setObject(object);
                authorization.setAction(action);
                
                authlist.add(authorization);
                
                if (parameters == null)
                    continue;
                
                for (Object parameter : parameters) {
                    resultmap = (Map<String, Object>)parameter;
                    name = (String)resultmap.get("PARAM");
                    value = (String)resultmap.get("VALUE");
                    authorization.add(name, value);
                }
            }
        }
        
        if (authlist.size() == 0) {
            return null;
        } else {
            autharray = authlist.toArray(new Authorization[0]);
            authorizations.put(username, autharray);
            
            return autharray;
        }
    }
    
    public static final void invalidateCache() {
        authorizations.clear();
    }
}
