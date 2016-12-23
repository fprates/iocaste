package org.quantic.template;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public final void entries() {
        instance("pt_BR");
        put("TESTE", "Aplicação template");
        put("iocaste-template", "Módulo template");
        put("server.test", "Clique no botão para testar o servidor.");
    }
}
