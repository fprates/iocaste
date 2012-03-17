package org.iocaste.datadict;

import org.iocaste.packagetool.common.InstallData;

public class Install {

    public static InstallData self() {
        InstallData data = new InstallData();
        
        data.link("SE11", "iocaste-datadict");
        data.link("DATADICT", "iocaste-datadict");
        
        return data;
    }
}
