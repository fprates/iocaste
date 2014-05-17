package org.iocaste.styleeditor;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        TaskGroup taskgroup;
        Authorization authorization;
        InstallData data = new InstallData();
        Map<String, String> messages = new HashMap<String, String>();
        
        messages.put("create", "Criar");
        messages.put("estilo", "Estilo");
        messages.put("show", "Exibir");
        messages.put("STYLE", "Editor de estilos");
        messages.put("update", "Editar");
        data.setMessages("pt_BR", messages);
        
        data.link("STYLE", "iocaste-styleeditor");
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("STYLE");
        data.add(taskgroup);
        
        authorization = new Authorization("STYLEEDITOR.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-styleeditor");
        data.add(authorization);
        
        authorization = new Authorization("STYLE.CALL");
        authorization.setObject("LINK");
        authorization.setAction("CALL");
        authorization.add("LINK", "STYLE");
        data.add(authorization);
        
        return data;
    }
}
