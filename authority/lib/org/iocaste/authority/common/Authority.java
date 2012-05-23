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
     * @param authorization
     * @throws Exception
     */
    public final void assign(String username, String profile,
            Authorization authorization) throws Exception {
        Message message = new Message();
        
        message.setId("assign");
        message.add("username", username);
        message.add("profile", profile);
        message.add("authorization", authorization);
        
        call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public final Authorization get(String name) throws Exception {
        Message message = new Message();
        
        message.setId("get");
        message.add("name", name);
        
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @throws Exception
     */
    public final void remove(String name) throws Exception {
        Message message = new Message();
        
        message.setId("remove");
        message.add("name", name);
        
        call(message);
    }
    
    /**
     * 
     * @param authorization
     * @throws Exception
     */
    public final void save(Authorization authorization) throws Exception {
        Message message = new Message();
        
        message.setId("save");
        message.add("authorization", authorization);
        
        call(message);
    }
}
