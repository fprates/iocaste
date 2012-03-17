package org.iocaste.dataview;

import org.iocaste.packagetool.common.InstallData;

public class Install {

    public static final InstallData self() {
        InstallData data = new InstallData();

        data.link("DATAVIEW", "iocaste-dataview");
        data.link("SE16", "iocaste-dataview");
        
        return data;
    }
}
