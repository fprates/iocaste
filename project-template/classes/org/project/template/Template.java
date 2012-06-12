package org.project.template;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

/**
 * Classe de interface entre o programa cliente
 * e as funções do servidor
 * @author francisco.prates
 *
 */
public class Template extends AbstractServiceInterface {
    private static final String SERVERNAME = "/template/services.html";
    public Template(Function function) {
        initService(function, SERVERNAME);
    }
    
    /**
     * Método que chama a função "get_message" do servidor
     * @return
     * @throws Exception
     */
    public final String getMessage() throws Exception {
        Message message = new Message();
        
        message.setId("get_message");
        
        return call(message);
    }
}
