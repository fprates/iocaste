package org.iocaste.packagetool;

import org.iocaste.packagetool.common.InstallData;

public class Install {

    public static final InstallData self() {
        InstallData data = new InstallData();
        
        data.link("PACKAGE", "iocaste-packagetool");
        
        return data;
    }
}
