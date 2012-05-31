package org.iocaste.tasksel;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;

public class Install {
    
    /**
     * 
     * @return
     */
    public static final InstallData init(Function function) throws Exception {
        Map<String, String> messages;
        InstallData data = new InstallData();
        
        messages = new HashMap<String, String>();
        messages.put("task-selector", "Seletor de tarefas");
        messages.put("run", "Executar");
        messages.put("command", "Comando");
        messages.put("command.not.found", "Comando não encontrado.");
        
        data.setMessages("pt_BR", messages);
        
        return data;
    }
}

