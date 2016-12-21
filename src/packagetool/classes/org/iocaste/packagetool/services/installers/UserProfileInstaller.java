package org.iocaste.packagetool.services.installers;

import java.util.Set;

import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;
import org.iocaste.protocol.user.UserProfile;

public class UserProfileInstaller
        extends AbstractModuleInstaller<UserProfile, Set<User>> {
    private Authority authority;
    private Documents documents;
    
    public UserProfileInstaller(Services services) {
        super(services, "AUTH_PROFILE");
    }

    @Override
    protected String getObjectName(UserProfile key, Set<User> object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init(Function function) {
        if (authority != null)
            return;
        authority = new Authority(function);
        documents = new Documents(function);
    }

    @Override
    public void install(State state) throws Exception {
        super.installAll(state, state.data.getUserProfiles());
    }

    @Override
    protected void install(State state, UserProfile profile, Set<User> users) {
        Set<String> uprofiles;
        String profilename, username;
        
        profilename = profile.getName();
        if (authority.getProfile(profilename) == null)
            authority.save(profile);

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

    @Override
    public void remove(ExtendedObject object) {
        authority.removeProfile(getObjectName(object));
        documents.delete(object);
    }

    @Override
    public void update(State state) throws Exception {
        super.installAll(state, state.data.getUserProfiles());
    }

    @Override
    protected void update(State state, UserProfile key, Set<User> object)
            throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}