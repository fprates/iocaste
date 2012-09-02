package org.iocaste.external;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        TaskGroup taskgroup;
        Authorization authorization;
        InstallData data = new InstallData();
        
        authorization = new Authorization("EXTERN.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-external");
        data.add(authorization);

        data.link("SE37", "iocaste-external");
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("SE37");
        data.add(taskgroup);
        
        return data;
    }
}
