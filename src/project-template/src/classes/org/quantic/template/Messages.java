package org.quantic.template;

import org.iocaste.shell.common.messages.AbstractMessages;

public class Messages extends AbstractMessages {

    @Override
    public final void entries() {
        locale("pt_BR");
        put("TESTE", "Aplicação template");
        put("iocaste-template", "Módulo template");
        put("server.test", "Clique no botão para testar o servidor.");
    }
}
