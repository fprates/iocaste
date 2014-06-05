package org.iocaste.install;

import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;

public class Packages {
    private static final String[] PACKAGES = new String[] {
            "iocaste-packagetool",
            "iocaste-tasksel",
            "iocaste-setup",
            "iocaste-search-help",
            "iocaste-appbuilder"
            };

    public static final void install(Function function) {
        PackageTool pkgtool;
        Iocaste iocaste;
        
        iocaste = new Iocaste(function);
        iocaste.login("ADMIN", "iocaste", "pt_BR");
        
        pkgtool = new PackageTool(function);
        for (String pkgname : PACKAGES)
            pkgtool.install(pkgname);

        iocaste.commit();
        iocaste.disconnect();
    }
}
