package org.iocaste.packagetool.main;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.packagetool.detail.DetailPackage;

public class MainPanel extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        put("indetail", new DetailPackage("inpackages"));
        put("undetail", new DetailPackage("unpackages"));
        put("erdetail", new DetailPackage("erpackages"));
        put("install", new InstallPackage());
        put("remove", new UninstallPackage());
        put("update", new UpdatePackage());
        put("reload", new Reload());
        
        getExtendedContext().getContext().run("main", "reload");
    }
    
}