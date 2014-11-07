package org.iocaste.kernel.users;

import org.iocaste.kernel.database.Database;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractFunction;

public class Users extends AbstractFunction {
    public Database database;
    public Session session;
    
    public Users() {
        export("create_user", new CreateUser());
        export("drop_user", new DropUser());
        export("get_user_data", new GetUserData());
        export("is_initial_secret", new IsInitialSecret());
        export("set_user_password", new SetUserPassword());
        export("update_user", new UpdateUser());
    }
}
