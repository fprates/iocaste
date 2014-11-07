package org.iocaste.kernel.authorization;

import org.iocaste.kernel.database.Database;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractFunction;

public class Auth extends AbstractFunction {
    public Session session;
    public Database database;
    
    public Auth() {
        export("is_authorized", new IsAuthorized());
    }

}
