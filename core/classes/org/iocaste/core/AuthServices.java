package org.iocaste.core;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.user.Authorization;

public class AuthServices {

    private static final int bdToInt(Object object) {
        return ((BigDecimal)object).intValue();
    }
    
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
            DBServices db, String username, String authname) throws Exception {
        int ident;
        Authorization authorization;
        Map<String, Object> resultmap;
        List<Authorization> authlist;
        String name, value, query = "select * from USERS004 where UNAME = ?";
        Object[] prmresult, authresult, prfresult =
                db.select(connection, query, 0, username);
        
        if (prfresult == null)
            return null;
        
        authlist = new ArrayList<Authorization>();
        
        for (Object prfobject : prfresult) {
            resultmap = (Map<String, Object>)prfobject;
            ident = bdToInt(resultmap.get("IDENT"));
            query = "select * from USERS002 where PRFID = ? and AUTNM = ?";
            authresult = db.select(connection, query, 0, ident, authname);
            
            if (authresult == null)
                continue;
            
            query = "select * from USERS003 where AUTID = ?";
            
            for (Object authobject : authresult) {
                resultmap = (Map<String, Object>)authobject;
                
                ident = bdToInt(resultmap.get("IDENT"));
                prmresult = db.select(connection, query, 0, ident);
                
                authorization = new Authorization();
                authorization.setName(authname);
                authlist.add(authorization);
                
                for (Object prmobject : prmresult) {
                    resultmap = (Map<String, Object>)prmobject;
                    name = (String)resultmap.get("PARAM");
                    value = (String)resultmap.get("VALUE");
                    authorization.add(name, value);
                }
            }
        }
        
        return (authlist.size() == 0)? null :
            authlist.toArray(new Authorization[0]);
    }
}
