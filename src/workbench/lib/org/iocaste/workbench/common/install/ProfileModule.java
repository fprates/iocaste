package org.iocaste.workbench.common.install;

import org.iocaste.protocol.utils.ConversionResult;

public class ProfileModule extends AbstractInstallModule {
    
    public ProfileModule(ModuleContext context) {
        super(context, "install", "profile");
    }
    
    protected final void execute(ConversionResult map) {
        String profile = map.getst("install.profile");
        modulectx.defaultinstall.setProfile(profile);
    }
}
