package org.iocaste.workbench.common.engine;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.workbench.common.install.DataElementModule;
import org.iocaste.workbench.common.install.InstallModule;
import org.iocaste.workbench.common.install.LinkModule;
import org.iocaste.workbench.common.install.ModelModule;
import org.iocaste.workbench.common.install.ModuleContext;
import org.iocaste.workbench.common.install.ProfileModule;

public class AutomatedInstall extends AbstractInstallObject {
    private Function function;
    private String xml;
    private PageBuilderDefaultInstall defaultinstall;
    
    public AutomatedInstall(Function function,
            PageBuilderDefaultInstall defaultinstall) {
        ModuleContext modulectx;
        String pkgname = defaultinstall.getPackage();
        Iocaste iocaste = new Iocaste(function);
        byte[] buffer = iocaste.getMetaContext(pkgname, "install.xml");
        
        xml = new String(buffer);
        this.function = function;
        this.defaultinstall = defaultinstall;
        
        modulectx = new ModuleContext(function);
        modulectx.context = null;
        modulectx.defaultinstall = defaultinstall;
        new LinkModule(modulectx);
        new ProfileModule(modulectx);

        install(modulectx);
    }

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModuleContext modulectx = new ModuleContext(function);
        
        modulectx.context = context;
        modulectx.defaultinstall = defaultinstall;
        new DataElementModule(modulectx);
        new ModelModule(modulectx);
        
        install(modulectx);
    }
    
    private void install(ModuleContext modulectx) {
        ConversionResult converted;
        InstallModule module;
        
        converted = modulectx.conversion.conversion(xml, modulectx.mapping);
        for (String key : converted.keySet()) {
            module = modulectx.modules.get(key);
            if (module == null)
                continue;
            module.setConverted(converted);
            module.run();
        }
    }
}
