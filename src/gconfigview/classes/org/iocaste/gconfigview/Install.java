package org.iocaste.gconfigview;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        
        authorization = new Authorization("GCONFIGVIEW.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-gconfigview");
        data.add(authorization);
        
        authorization = new Authorization("GCONFIGVIEW.CALL");
        authorization.setObject("LINK");
        authorization.setAction("CALL");
        authorization.add("LINK", "GCONFIGVIEW");
        data.add(authorization);
        
        messages = new HashMap<>();
        messages.put("config.display", "Exibir configurações");
        messages.put("config.edit","Editar configurações");
        messages.put("config.select","Selecionar configuração");
        messages.put("display", "Exibir");
        messages.put("edit", "Editar");
        messages.put("GCONFIGVIEW", "Configurações globais");
        messages.put("NAME", "Aplicação");
        messages.put("parameters.save.error",
                "Erro na gravação dos parâmetros");
        messages.put("save", "Gravar");
        messages.put("save.successful", "Configurações salvas com sucesso.");
        data.setMessages("pt_BR", messages);
        
        data.link("GCONFIGVIEW", "iocaste-gconfigview");
        taskgroup = new TaskGroup("ADMIN");
        taskgroup.add("GCONFIGVIEW");
        data.add(taskgroup);
        
        return data;
    }
}
