package org.iocaste.dataview;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData self() {
        InstallData data = new InstallData();
        Authorization authorization;
        
        authorization = new Authorization("DATAVIEWER.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-dataview");
        data.add(authorization);
        
        data.link("DATAVIEW", "iocaste-dataview");
        data.link("SE16", "iocaste-dataview");
        
        return data;
    }
}
