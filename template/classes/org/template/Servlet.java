package org.template;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#config()
     * 
     * registre as bibliotecas aqui com register()
     */
    @Override
    public void config() {
        
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#preRun(
     *     org.iocaste.protocol.Message)
     * 
     * rotinas customizadas de pré-processamento
     */
    @Override
    public void preRun(Message message) {
        super(message);
    }
}
