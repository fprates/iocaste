package org.iocaste.core;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.user.Authorization;

public class AuthServices {

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
        Authorization autobject;
        List<Authorization> autobjects;
        Map<String, Object> resultmap;
        String name, value, query =
                "select * from USERS002 where UNAME = ? and AUTNM = ?";
        Object[] result_, result =
                db.select(connection, query, 0, username, authname);
        
        if (result == null)
            return null;
        
        autobjects = new ArrayList<Authorization>();
        for (Object object : result) {
            resultmap = (Map<String, Object>)object;
            
            query = "select * from USERS003 where AUTID = ?";
            result_ = db.select(connection, query, 0, resultmap.get("IDENT"));
            autobject = new Authorization();
            autobject.setName(authname);
            autobjects.add(autobject);
            
            for (Object object_ : result_) {
                resultmap = (Map<String, Object>)object_;
                name = (String)resultmap.get("PARAM");
                value = (String)resultmap.get("VALUE");
                autobject.add(name, value);
            }
        }
        
        return (autobjects.size() == 0)? null :
            autobjects.toArray(new Authorization[0]);
    }
}
