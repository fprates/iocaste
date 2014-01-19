package org.iocaste.packagetool;

import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Request {
    
    public static final void info(Context context) {
        String pkgname;
        Table table = context.view.getElement("packages");
        
        for (TableItem item : table.getItems())
            if (item.isSelected()) {
                context.view.redirect("printinfo");
                pkgname = ((Text)item.get("name")).getName();
                context.data = new PackageTool(context.function).
                        getInstallData(pkgname);
            }
    }
    
    public static final void packageInstall(Context context) {
        String pkgname = ((InputComponent)context.view.getElement("package")).
                get();
        PackageTool pkgtool = new PackageTool(context.function);
        
        pkgtool.install(pkgname);
        updatePackageAction(context.view, pkgname, "packageuninstall");
        
        context.view.message(Const.STATUS, "package.installed");
    }
    
    public static final void packageUninstall(Context context) {
        String pkgname = ((InputComponent)context.view.getElement("package")).
                get();
        PackageTool pkgtool = new PackageTool(context.function);
        
        pkgtool.uninstall(pkgname);
        updatePackageAction(context.view, pkgname, "packageinstall");
        
        context.view.message(Const.STATUS, "package.uninstalled");
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
        
        for (TableItem item : table.getItems()) {
            link = item.get("action");
            if (!link.getName().equals(pkgname))
                continue;

            link.setText(action);
            link.setAction(action);
            break;
        }
    }

}
