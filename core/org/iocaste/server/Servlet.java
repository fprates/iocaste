package org.iocaste.server;

import org.iocaste.components.login.Login;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -8569034003940826582L;
    private Login login;
    
    @Override
    public void config() {
        login = new Login();
        register(login);
    }
    
    /**
     * Permite chamadas à login() mesmo sem sessão.
     * Para outras chamadas, verifica validade da sessão.
     */
    @Override
    public void preRun(Message message) throws Exception {
        Message test;
        
        if (message.getId().equals("login"))
            return;
        
        test = new Message();
        
        test.add("sessionid", message.getSessionid());
        
        if (!login.isConnected(test))
            throw new Exception("Invalid session.");
    }
}
