package org.iocaste.packagetool;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.StandardList;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    public final InstallData install(Message message) {
        return Install.self();
    }
    
    public final void main(ViewData view) throws Exception {
        Link link;
        String action;
        Container container = new Form(view, "main");
        Parameter parameter = new Parameter(container, "package");
        String[] packages = PackageTool.getAvailablePackages();
        StandardList list = new StandardList(container, "packages");
        PackageTool pkgtool = new PackageTool(this);
        
        for (String pkgname : packages) {
            action = (pkgtool.isInstalled(pkgname))?
                    "packageuninstall" : "packageinstall";
            link = new Link(list, pkgname, action);
            link.setText(new StringBuilder(action).append(" ").append(pkgname).
                    toString());
            link.add(parameter, pkgname);
        }
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle("package.manager");
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void packageinstall(ViewData view) throws Exception {
        String pkgname = ((InputComponent)view.getElement("package")).get();
        PackageTool pkgtool = new PackageTool(this);
        
        pkgtool.install(pkgname);
        
        view.setReloadableView(true);
        view.message(Const.STATUS, "package.installed");
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void packageuninstall(ViewData view) throws Exception {
        String pkgname = ((InputComponent)view.getElement("package")).get();
        PackageTool pkgtool = new PackageTool(this);
        
        pkgtool.uninstall(pkgname);

        view.setReloadableView(true);
        view.message(Const.STATUS, "package.uninstalled");
    }
}
