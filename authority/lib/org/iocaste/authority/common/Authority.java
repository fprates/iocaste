package org.iocaste.authority.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

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
     * @param name
     * @throws Exception
     */
    public final void remove(String name) throws Exception {
        Message message = new Message();
        
        message.setId("remove");
        message.add("name", name);
        
        call(message);
    }
}
