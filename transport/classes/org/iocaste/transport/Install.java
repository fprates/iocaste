package org.iocaste.transport;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        InstallData data = new InstallData();
        Authorization authorization = new Authorization("TRANSPORT.EXECUTE");
        
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-transport");
        
        data.add(authorization);
        data.link("SE09", "iocaste-transport");
        
        return data;
    }
}
