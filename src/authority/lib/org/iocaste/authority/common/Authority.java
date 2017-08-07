package org.iocaste.authority.common;

import java.util.Set;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Authority extends AbstractServiceInterface {
    public static final String SERVICE = "/iocaste-kernel/service.html";
    
    /**
     * 
     * @param function
     */
    public Authority(Function function) {
        initService(function, SERVICE);
    }
    
    /**
     * Adds authorization to a profile
     * @param profile authorizations' profile
     * @param authorization authorization
     */
    public final void addAuthorization(String profile,
            Authorization authorization) {
        Message message = new Message("add_authorization");
        message.add("profile", profile);
        message.add("authorization", authorization);
        call(message);
    }
    
    /**
     * 
     * @param username
     * @param profile
     */
    public final void assign(String username, String profile) {
        Message message = new Message("assign_profile");
        message.add("username", username);
        message.add("profile", profile);
        call(message);
    }
    
    /**
     * 
     * @param username
     * @param profile
     * @param authorization
     */
    public final void assign(String username, String profile,
            Authorization authorization) {
        Message message = new Message("assign_authorization");
        message.add("username", username);
        message.add("profile", profile);
        message.add("authorization", authorization);
        call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final Authorization get(String name) {
        Message message = new Message("get");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final UserProfile getProfile(String name) {
        Message message = new Message("get_profile");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * 
     * @param username
     * @return
     */
    public final Set<String> getUserProfiles(String username) {
        Message message = new Message("get_user_profiles");
        message.add("username", username);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final int remove(String name) {
        Message message = new Message("remove");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Remove perfil de autorizações
     * @param name nome do perfil
     * @return 1, se removido com sucesso.
     */
    public final int removeProfile(String name) {
        Message message = new Message("remove_profile");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * 
     * @param authorization
     */
    public final void save(Authorization authorization) {
        Message message = new Message("save");
        message.add("authorization", authorization);
        call(message);
    }
    
    /**
     * 
     * @param profile
     */
    public final void save(UserProfile profile) {
        Message message = new Message("save_profile");
        message.add("profile", profile);
        call(message);
    }
}
