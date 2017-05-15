package org.iocaste.runtime.common.install;

import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.Services;

public class InstallApplication extends AbstractHandler {
    private InstallContext installctx;
    
    public InstallApplication() {
        installctx = new InstallContext();
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Map<String, AbstractInstallObject> objects;
        Services<Context> services = getFunction();
        String pkgname = message.getst("name");

        if (installctx.isReady())
            return installctx.getInstallData();
        installctx.put("default", new ApplicationInstall(pkgname));
        services.getApplication().install(installctx);
        objects = installctx.getObjects();
        for (String name : objects.keySet())
            objects.get(name).run(installctx);
        installctx.ready();
        return installctx.getInstallData();
    }
    
}
