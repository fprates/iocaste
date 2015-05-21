package org.quantic.template.common;

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
    private static final String SERVERNAME = "/externaltest/services.html";
    public Template(Function function) {
        initService(function, SERVERNAME);
    }
    
    /**
     * Método que chama a função "get_message" do servidor
     * @return
     */
    public final String getMessage() {
        return call(new Message("get_message"));
    }
}
