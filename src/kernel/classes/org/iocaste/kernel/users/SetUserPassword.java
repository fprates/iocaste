package org.iocaste.kernel.users;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class SetUserPassword extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        UpdateUser update;
        User user;
        String sessionid = message.getSessionid();
        String secret = message.getst("secret");
        Users users = getFunction();
        
        user = users.session.sessions.get(sessionid).getUser();
        user.setSecret(secret);
        user.setInitialSecret(false);
        
        update = getFunction().get("update_user");
        update.run(user, sessionid);
        return null;
    }

}
