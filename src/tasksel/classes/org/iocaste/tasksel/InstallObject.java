package org.iocaste.tasksel;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.packagetool.common.InstallData;

public class InstallObject extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        Map<String, String> messages;
        InstallData data = new InstallData();
        
        messages = new HashMap<>();
        messages.put("command", "Comando");
        messages.put("command.not.found", "Comando não encontrado.");
        messages.put("not.authorized", "Sem autorização para executar.");
        messages.put("run", "Executar");
        messages.put("task-selector", "Seletor de tarefas");
        
        data.setMessages("pt_BR", messages);
    }
}

