package org.iocaste.protocol;

public final class Iocaste extends AbstractServiceInterface {
    public static final String SERVERNAME = "/iocaste-server/service.html";
    
    public Iocaste(Function function) {
        initService(function, SERVERNAME);
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public final boolean isConnected() throws Exception {
        Message message = new Message();
        
        message.setId("is_connected");
        
        return (Boolean)call(message);
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public final String getUsername() throws Exception {
        Message message = new Message();
        
        message.setId("get_username");
        
        return (String)call(message);
    }
    
    /**
     * 
     * @param user
     * @param secret
     * @return
     * @throws Exception
     */
    public final boolean login(String user, String secret) throws Exception {
        Message message = new Message();
        
        message.setId("login");
        message.add("user", user);
        message.add("secret", secret);
        
        return (Boolean)call(message);
    }
}
