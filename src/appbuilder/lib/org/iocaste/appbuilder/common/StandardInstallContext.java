package org.iocaste.appbuilder.common;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;

public class StandardInstallContext {
    private Map<String, AbstractInstallObject> objects;
    private InstallData data;
    
    public StandardInstallContext() {
        objects = new LinkedHashMap<>();
        data = new InstallData();
    }
    
    public final InstallData getInstallData() {
        return data;
    }
    
    public final Map<String, AbstractInstallObject> getObjects() {
        return objects;
    }
    
    public final void put(String name, AbstractInstallObject object) {
        objects.put(name, object);
    }
}
