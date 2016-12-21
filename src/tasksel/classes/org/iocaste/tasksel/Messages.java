package org.iocaste.tasksel;

import org.iocaste.shell.common.messages.AbstractMessages;

public class Messages extends AbstractMessages {

    @Override
    public void entries() {
        locale("pt_BR");
        put("command", "Comando");
        put("command.not.found", "Comando não encontrado.");
        put("not.authorized", "Sem autorização para executar.");
        put("run", "Executar");
        put("iocaste-tasksel", "Seletor de tarefas");
    }

}
