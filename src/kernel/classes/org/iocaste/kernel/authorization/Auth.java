package org.iocaste.kernel.authorization;

import org.iocaste.kernel.common.AbstractFunction;
import org.iocaste.kernel.database.Database;
import org.iocaste.kernel.session.Session;

public class Auth extends AbstractFunction {
    public Session session;
    public Database database;
    
    public Auth() {
        export("is_authorized", new IsAuthorized());
    }

}
