package org.iocaste.packagetool;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Request {
    
    public static final InstallData info(View view, Function function) {
        String pkgname;
        Table table = view.getElement("packages");
        
        for (TableItem item : table.getItens())
            if (item.isSelected()) {
                view.redirect("printinfo");
                pkgname = ((Text)item.get("name")).getName();
                return new PackageTool(function).getInstallData(pkgname);
            }
        
        return null;
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void packageInstall(View view, Function function) {
        String pkgname = ((InputComponent)view.getElement("package")).get();
        PackageTool pkgtool = new PackageTool(function);
        
        pkgtool.install(pkgname);
        updatePackageAction(view, pkgname, "packageuninstall");
        
        view.message(Const.STATUS, "package.installed");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void packageUninstall(View view, Function function) {
        String pkgname = ((InputComponent)view.getElement("package")).get();
        PackageTool pkgtool = new PackageTool(function);
        
        pkgtool.uninstall(pkgname);
        updatePackageAction(view, pkgname, "packageinstall");
        
        view.message(Const.STATUS, "package.uninstalled");
    }
    
    /**
     * 
     * @param view
     * @param pkgname
     * @param action
     */
    private static final void updatePackageAction(View view, String pkgname,
            String action) {
        Link link;
        Table table = view.getElement("packages");
        
        for (TableItem item : table.getItens()) {
            link = item.get("action");
            if (!link.getName().equals(pkgname))
                continue;

            link.setText(action);
            link.setAction(action);
            break;
        }
    }

}
