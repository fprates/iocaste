package org.iocaste.infosys;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {
    
    @Override
    public final void entries() {
        instance("pt_BR");
        put("CONNECTION_ID", "Conexão");
        put("CREATED_ON", "Criado em");
        put("ASSIGNED_TO", "Atribuído à");
    }
}