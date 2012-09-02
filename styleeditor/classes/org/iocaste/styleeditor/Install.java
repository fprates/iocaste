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
        
        messages.put("STYLE", "Editor de estilos");
        data.setMessages("pt_BR", messages);
        
        authorization = new Authorization("STYLEEDITOR.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-styleeditor");
        data.add(authorization);
        
        data.link("STYLE", "iocaste-styleeditor");
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("STYLE");
        data.add(taskgroup);
        
        return data;
    }
}
