package org.iocaste.tasksel;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public void entries() {
        instance("pt_BR");
        put("command", "Comando");
        put("command.not.found", "Comando não encontrado.");
        put("main", "Seletor de tarefas");
        put("not.authorized", "Sem autorização para executar.");
        put("run", "Executar");
        put("iocaste-tasksel", "Seletor de tarefas");
    }

}
