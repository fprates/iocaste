package org.iocaste.kernel.authorization;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.database.Select;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;

public class IsAuthorized extends AbstractHandler {
    private static final byte USER_AUTHORITY = 0;
    private static final byte USER_PROFILE_ITEM = 1;
    private static final byte AUTHORIZATION_ITEM = 2;
    private static final String[] QUERIES = {
        "select * from USERS002 where UNAME = ?",
        "select * from AUTH004 where PRFNM = ?",
        "select * from AUTH002 where AUTNM = ?"
    };
    private static Logger logger;
    
    static {
        logger = Logger.getLogger(IsAuthorized.class.getName());
    }
            
    /**
     * 
     * @param connection
     * @param db
     * @param username
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private final List<Authorization> getAuthorizations(Connection connection,
            String username) throws Exception {
        Authorization authorization;
        Map<String, Object> resultmap;
        List<Authorization> authlist;
        String profilename, authname, name, value, object, action;
        Object[] parameters, profileitens, profiles;
        Select select;
        Auth auth;
        
        auth = getFunction();
        select = auth.documents.database.get("select");
        profiles = select.run(connection, QUERIES[USER_AUTHORITY], 0, username);
        
        if (profiles == null)
            return null;
        
        authlist = new ArrayList<>();
        for (Object profile : profiles) {
            resultmap = (Map<String, Object>)profile;
            profilename = (String)resultmap.get("PRFNM");
            profileitens = select.run(connection, QUERIES[USER_PROFILE_ITEM],
                    0, profilename);
            
            if (profileitens == null)
                continue;
            
            for (Object profileitem : profileitens) {
                resultmap = (Map<String, Object>)profileitem;
                authname = (String)resultmap.get("AUTNM");
                object = (String)resultmap.get("OBJCT");
                action = (String)resultmap.get("ACTIO");
                parameters = select.run(connection, QUERIES[AUTHORIZATION_ITEM],
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
        
        return (authlist.size() == 0)? null : authlist;
    }

    private boolean check(List<Authorization> usrauthorizations,
            String key, String value) {
        String usrvalue;
        Map<String, String> usrparameters;
        
        for (Authorization usrauthorization : usrauthorizations) {
            usrparameters = usrauthorization.getParameters();
            usrvalue = usrparameters.get(key);
            if ((usrvalue == null) || !usrvalue.equals(value))
                continue;
            return false;
        }
        
        return true;
    }
    
    @Override
    public Object run(Message message) throws Exception {
        boolean fail;
        User user;
        String objvalue, username;
        Map<String, String> objparameters;
        List<Authorization> usrauthorizations;
        Connection connection;
        Authorization objauthorization;
        Auth auth = getFunction();
        String sessionid = message.getSessionid();
        UserContext context = auth.session.sessions.get(sessionid).usercontext;
        
        if (context == null && auth.isAuthorizedCall())
            return false;

        user = context.getUser();
        if (user == null)
            return false;
        
        objauthorization = message.get("authorization");
        usrauthorizations = context.getAuthorizations();
        username = user.getUsername();
        if (usrauthorizations == null) {
            connection = auth.documents.database.getDBConnection(sessionid);
            usrauthorizations = getAuthorizations(connection, username);
            context.setAuthorizations(usrauthorizations);
        }
        
        if (usrauthorizations == null)
            return false;
        
        objparameters = objauthorization.getParameters();
        for (String key : objparameters.keySet()) {
            objvalue = objparameters.get(key);
            if (objvalue == null || objvalue.length() == 0)
                continue;
            
            fail = check(usrauthorizations, key, objvalue);
            if (!fail)
                continue;
            
            logger.warning(new StringBuilder("invalid authorization '").
                    append(objauthorization.getName()).append("@").
                    append(key).append("=").
                    append(objvalue).append("' for ").
                    append(username).toString());
            return false;
        }
        
        return true;
    }

}
