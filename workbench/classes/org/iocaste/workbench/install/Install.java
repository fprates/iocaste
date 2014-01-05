package org.iocaste.workbench.install;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {

    public static final InstallData execute() {
        UserProfile profile;
        TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        
        messages = new HashMap<>();
        messages.put("iocaste-workbench", "Workbench");
        messages.put("WORKBENCH", "Workbench");
        data.setMessages("pt_BR", messages);
        
        authorization = new Authorization("WORKBENCH.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-workbench");
        data.add(authorization);
        
        profile = new UserProfile("DEVELOPER");
        profile.add(authorization);
        data.add(profile);
        
        data.link("WORKBENCH", "iocaste-workbench");
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("WORKBENCH");
        data.add(taskgroup);
        
        data.addText("WB_SOURCES");
        
        Models.install(data);
        
        return data;
    }
}
