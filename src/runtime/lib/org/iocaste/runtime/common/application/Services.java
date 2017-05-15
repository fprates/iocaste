package org.iocaste.runtime.common.application;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.runtime.common.install.InstallApplication;

public class Services<T extends Context> extends AbstractFunction {
    private Application<T> application;
    
    public Services(Application<T> application) {
        this.application = application;
        export("install", new InstallApplication());
    }
    
    public final Application<T> getApplication() {
        return application;
    }
}
