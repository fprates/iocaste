package org.iocaste.authority.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;

public class Authority extends AbstractServiceInterface {
    private static final String SERVER = "/iocaste-authority/services.html";
    
    /**
     * 
     * @param function
     */
    public Authority(Function function) {
        initService(function, SERVER);
    }
    
    /**
     * 
     * @param username
     * @param profile
     */
    public final void assign(String username, String profile) {
        Message message = new Message();
        
        message.setId("assign_profile");
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
        Message message = new Message();
        
        message.setId("assign_authorization");
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
        Message message = new Message();
        
        message.setId("get");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final int remove(String name) {
        Message message = new Message();
        
        message.setId("remove");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * 
     * @param authorization
     */
    public final void save(Authorization authorization) {
        Message message = new Message();
        
        message.setId("save");
        message.add("authorization", authorization);
        call(message);
    }
}
