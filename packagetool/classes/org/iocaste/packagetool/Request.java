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
    
    public static final void info(View view, Function function) {
        InstallData data;
        String pkgname;
        Table table = view.getElement("packages");
        
        for (TableItem item : table.getItens())
            if (item.isSelected()) {
                pkgname = ((Text)item.get("name")).getName();
                data = new PackageTool(function).getInstallData(pkgname);
                
                /*
                 * não passa InstallData, pois não é reconhecido pelo núcleo.
                 * gera erro de serialização.
                 */
                view.clearExports();
                view.export("models", data.getModels());
                view.export("authorizations", data.getAuthorizations());
                view.export("links", data.getLinks());
                view.export("numbers", data.getNumberFactories());
                view.export("dependencies", data.getDependencies());
                view.redirect("printinfo");
                
                break;
            }
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
