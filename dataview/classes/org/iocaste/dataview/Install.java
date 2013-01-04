package org.iocaste.dataview;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {

    public static final InstallData init() {
        TaskGroup taskgroup;
        UserProfile profile;
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        
        authorization = new Authorization("DATAVIEWER.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-dataview");
        data.add(authorization);
        
        profile = new UserProfile("DEVELOP");
        profile.add(authorization);
        data.add(profile);
        
        messages = new HashMap<>();
        messages.put("dataview-selection", "Seleção de modelo");
        messages.put("invalid.model", "Modelo inválido.");
        messages.put("is.reference.model", "Modelo apenas para referência.");
        messages.put("NAME", "Modelo");
        messages.put("SE16", "Visão de entradas em modelos");
        messages.put("select", "Selecionar");
        data.setMessages("pt_BR", messages);
        
        data.link("SE16", "iocaste-dataview");
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("SE16");
        data.add(taskgroup);
        
        return data;
    }
}
