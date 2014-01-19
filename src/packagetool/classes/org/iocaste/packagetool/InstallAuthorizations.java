package org.iocaste.packagetool;

import java.util.Map;
import java.util.Set;

import org.iocaste.authority.common.Authority;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;
import org.iocaste.protocol.user.UserProfile;

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
    }
    
    public static final void init(Map<UserProfile, Set<User>> profiles,
            State state) {
        Set<String> uprofiles;
        String profilename, username;
        Set<User> users;
        Authority authority = new Authority(state.function);
        
        for (UserProfile profile : profiles.keySet()) {
            profilename = profile.getName();
            if (authority.getProfile(profilename) == null) {
                authority.save(profile);
                Registry.add(profilename, "AUTH_PROFILE", state);
            }

            users = profiles.get(profile);
            for (Authorization authorization : profile.getAuthorizations()) {
                authority.addAuthorization(profilename, authorization);
                
                for (User user : users) {
                    username = user.getUsername();
                    uprofiles = authority.getUserProfiles(username);
                    if (uprofiles == null || !uprofiles.contains(profilename))
                        authority.assign(username, profilename);
                    
                    authority.assign(username, "BASE");
                }
            }
        }
    }

}
