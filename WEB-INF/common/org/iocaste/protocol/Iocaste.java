package org.iocaste.protocol;

public final class Iocaste extends AbstractServiceInterface {
    
    public Iocaste(Module module) throws Exception {
        initService(module, "http://localhost:8080/iocaste-server/index.html");
    }
    
    public final boolean login(String user, String secret) throws Exception {
        Message message = new Message();
        
        message.setId("login");
        message.add("user", user);
        message.add("secret", secret);
        
        return (Boolean)call(message);
    }
    
    public final boolean isConnected() throws Exception {
        Message message = new Message();
        
        message.setId("is_connected");
        
        return (Boolean)call(message);
    }
}
