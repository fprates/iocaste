package org.iocaste.appbuilder.common;

import org.iocaste.packagetool.common.InstallData;

public abstract class AbstractInstallObject {
    private InstallData data;
    
    public abstract void execute();
    
    protected final InstallData getInstallData() {
        return data;
    }
    
    public final void setInstallData(InstallData data) {
        this.data = data;
    }
}
