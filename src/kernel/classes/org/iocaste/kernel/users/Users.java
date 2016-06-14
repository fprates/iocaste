package org.iocaste.kernel.users;

import org.iocaste.kernel.database.Database;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractFunction;

public class Users extends AbstractFunction {
    public static final byte USER_GET = 0;
    public static final byte SECRET_QUERY = 1;
    public static final byte INIT_QUERY = 2;
    public static final String[] QUERIES = {
            "select UNAME, SECRT, FNAME, SNAME from USERS001 where UNAME = ?",
            "select SECRT from USERS001 where UNAME = ?",
            "select INIT from USERS001 where UNAME = ?"
    };
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
