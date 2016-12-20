package org.iocaste.packagetool.services;

import java.util.Map;
import java.util.Set;

import org.iocaste.authority.common.Authority;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;
import org.iocaste.protocol.user.UserProfile;

public class InstallAuthorizations {
    
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
