package org.iocaste.core;


import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -8569034003940826582L;
    private Services login;
    
    @Override
    public void config() {
        login = new Services();
        register(login);
    }
    
    /**
     * Permite chamadas à login() e isConnected() mesmo sem sessão.
     * Para outras chamadas, verifica validade da sessão.
     */
    @Override
    public void preRun(Message message) throws Exception {
        if (message.getId().equals("login") ||
                message.getId().equals("is_connected"))
            return;
        
        if (!login.isConnected(message))
            throw new Exception("Invalid session.");
    }
}
