package org.iocaste.dataeditor;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {

    public static final InstallData init() {
        UserProfile profile;
        TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        
        authorization = new Authorization("DATAEDITOR.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-dataeditor");
        data.add(authorization);
        
        profile = new UserProfile("DEVELOP");
        profile.add(authorization);
        data.add(profile);
        
        messages = new HashMap<>();
        messages.put("dataeditor-selection", "Seleção de modelo");
        messages.put("delete", "Remover");
        messages.put("edit", "Editar");
        messages.put("insert", "Inserir");
        messages.put("insert.successful", "Item inserido com sucesso.");
        messages.put("insertitem", "Inserir");
        messages.put("insertnext", "Próximo");
        messages.put("model.name", "Modelo");
        messages.put("save", "Salvar");
        messages.put("SM30", "Editor de entradas em modelos");
        data.setMessages("pt_BR", messages);
        
        data.link("SM30", "iocaste-dataeditor");
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("SM30");
        data.add(taskgroup);
        
        return data;
    }
}
