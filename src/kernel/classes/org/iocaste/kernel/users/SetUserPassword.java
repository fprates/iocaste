package org.iocaste.kernel.users;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class SetUserPassword extends AbstractHandler {
    public Session session;

    @Override
    public Object run(Message message) throws Exception {
        UpdateUser update;
        User user;
        String sessionid = message.getSessionid();
        String secret = message.getString("secret");
        
        user = session.sessions.get(sessionid).getUser();
        user.setSecret(secret);
        user.setInitialSecret(false);
        
        update = getFunction().get("update_user");
        update.run(user, sessionid);
        return null;
    }

}
