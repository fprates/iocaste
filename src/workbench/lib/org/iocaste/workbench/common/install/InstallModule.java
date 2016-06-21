package org.iocaste.workbench.common.install;

import org.iocaste.protocol.utils.ConversionResult;

public interface InstallModule {

    public abstract void run();
    
    public abstract void setConverted(ConversionResult converted);
}
