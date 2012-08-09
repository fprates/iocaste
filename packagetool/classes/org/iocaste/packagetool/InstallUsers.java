package org.iocaste.packagetool;

import java.util.Set;

import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;

public class InstallUsers {

    public static final void init(Set<User> users, State state) {
        Iocaste iocaste = new Iocaste(state.function);
        
        for (User user : users)
            iocaste.create(user);
    }
}
