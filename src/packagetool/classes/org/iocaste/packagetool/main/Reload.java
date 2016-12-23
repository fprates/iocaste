package org.iocaste.packagetool.main;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.Context;
import org.iocaste.packagetool.Main;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.packagetool.services.IsInstalled;
import org.iocaste.protocol.Iocaste;

public class Reload extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        IsInstalled isinstalled;
        ExtendedObject object;
        String[] packages;
        PackageTool pkgtool;
        Context extcontext;
        
        packages = new Iocaste(context.function).getAvailablePackages();
        pkgtool = new PackageTool(context.function);
        isinstalled = context.function.get("is_installed");
        extcontext = getExtendedContext();
        
        for (String name : packages) {
            object = instance("PACKAGE_GRID");
            object.set("NAME", name);
            
            try {
                pkgtool.getInstallData(name);
            } catch (Exception e) {
                Main.registerException(name, extcontext, e);
                object.set("EXCEPTION", e.getMessage());
                extcontext.invalid.add(object);
                continue;
            }
            
            if (isinstalled.run(name))
                extcontext.installed.add(object);
            else
                extcontext.uninstalled.add(object);
        }
    }

}
