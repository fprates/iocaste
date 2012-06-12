package org.project.template;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        
        // mensagens
        messages = new HashMap<String, String>();
        messages.put("TESTE", "Aplicação template");
        messages.put("iocaste-template", "Módulo template");
        messages.put("server.test", "Clique no botão para testar o servidor.");
        data.setMessages("pt_BR", messages);
        
        // autorização de execução
        authorization = new Authorization("TESTE.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "template");
        data.add(authorization);
        
        // link
        data.link("TESTE", "template");
        data.addTaskGroup("TESTE", "TESTE");
        
        return data;
    }
}
