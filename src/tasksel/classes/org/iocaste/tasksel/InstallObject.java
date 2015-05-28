package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class InstallObject extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages;
        
        messages = messageInstance("pt_BR");
        messages.put("command", "Comando");
        messages.put("command.not.found", "Comando não encontrado.");
        messages.put("not.authorized", "Sem autorização para executar.");
        messages.put("run", "Executar");
        messages.put("iocaste-tasksel", "Seletor de tarefas");
    }
}

