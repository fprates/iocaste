package org.iocaste.packagetool;

import org.iocaste.authority.common.Authority;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.Authorization;

public class InstallAuthorizations {
    
    /**
     * 
     * @param authorizations
     * @param state
     */
    public static final void init(Authorization[] authorizations, State state) {
        String name;
        Authority authority = new Authority(state.function);
        
        for (Authorization authorization : authorizations) {
            name = authorization.getName();
            if (authority.get(name) == null)
                authority.save(authorization);
            
            authority.assign("ADMIN", "ALL", authorization);
            Registry.add(name, "AUTHORIZATION", state);
        }
        
        new Iocaste(state.function).invalidateAuthCache();
    }

}
