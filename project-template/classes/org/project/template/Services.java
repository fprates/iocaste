package org.project.template;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

/**
 * Implementação das funções do servidor
 * @author francisco.prates
 *
 */
public class Services extends AbstractFunction {

    public Services() {
        export("get_message", "getMessage");
    }
    
    public final String getMessage(Message message) {
        return "Olá, enfermeira!";
    }
}
