package org.iocaste.kernel.database;

import org.iocaste.kernel.session.Login;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class InstanceExternalDB extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Database function = getFunction();
        Login login = function.session.get("login");
        String dbname = message.getst("dbname");
        
        return login.run(message.getSessionid(), dbname);
    }

}
