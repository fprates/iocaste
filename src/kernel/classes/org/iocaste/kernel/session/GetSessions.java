package org.iocaste.kernel.session;

import java.util.Set;
import java.util.TreeSet;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.common.Message;
import org.iocaste.kernel.common.user.User;

public class GetSessions extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        UserContext context;
        User user;
        Set<String> users;
        Session session = getFunction();
        
        users = new TreeSet<>();
        for (String sessionid : session.sessions.keySet()) {
            context = session.sessions.get(sessionid);
            user = context.getUser();
            if (user == null)
                continue;
            
            users.add(sessionid);
        }
        
        return users;
    }

}
