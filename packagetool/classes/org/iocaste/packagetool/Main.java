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
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final InstallData install(Message message) throws Exception {
        return Install.init(this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void main(ViewData view) throws Exception {
        Link link;
        TableItem item;
        String action;
        Container container = new Form(view, "main");
        Parameter parameter = new Parameter(container, "package");
        String[] packages = PackageTool.getAvailablePackages();
        PackageTool pkgtool = new PackageTool(this);
        Table table = new Table(container, "packages");
        
        new TableColumn(table, "name");
        new TableColumn(table, "action");
        
        for (String pkgname : packages) {
            action = pkgtool.isInstalled(pkgname)?
                    "packageuninstall" : "packageinstall";
            
            item = new TableItem(table);
            item.add(new Text(table, pkgname));
            
            link = new Link(table, pkgname, action);
            link.setText(action);
            link.add(parameter, pkgname);
            item.add(link);
        }
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle("package-manager");
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
