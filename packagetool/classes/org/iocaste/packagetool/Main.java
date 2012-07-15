package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void info(View view) {
        InstallData data;
        String pkgname;
        Table table = view.getElement("packages");
        
        for (TableItem item : table.getItens())
            if (item.isSelected()) {
                pkgname = ((Text)item.get("name")).getName();
                data = new PackageTool(this).getInstallData(pkgname);
                
                /*
                 * não passa InstallData, pois não é reconhecido pelo núcleo.
                 * gera erro de serialização.
                 */
                view.clearExports();
                view.setReloadableView(true);
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
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init(this);
    }
    
    /**
     * 
     * @param view
     */
    public final void main(View view) {
        Link link;
        TableItem item;
        String action;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        Parameter parameter = new Parameter(container, "package");
        String[] packages = PackageTool.getAvailablePackages();
        PackageTool pkgtool = new PackageTool(this);
        Button info = new Button(container, "info");
        Table table = new Table(container, "packages");
        
        pagecontrol.add("home");
        table.setMark(true);
        table.setSelectionType(Table.SINGLE);
        
        new TableColumn(table, "name");
        new TableColumn(table, "action");
        
        for (String pkgname : packages) {
            try {
                pkgtool.getInstallData(pkgname);
            } catch (Exception e) {
                continue;
            }
            
            action = pkgtool.isInstalled(pkgname)?
                    "packageuninstall" : "packageinstall";
            
            item = new TableItem(table);
            item.add(new Text(table, pkgname));
            
            link = new Link(table, pkgname, action);
            link.setText(action);
            link.add(parameter, pkgname);
            item.add(link);
        }
        
        info.setVisible(table.length() > 0);
        view.setTitle("package-manager");
    }
    
    /**
     * 
     * @param view
     */
    public final void packageinstall(View view) {
        String pkgname = ((InputComponent)view.getElement("package")).get();
        PackageTool pkgtool = new PackageTool(this);
        
        pkgtool.install(pkgname);
        view.setReloadableView(true);
        view.message(Const.STATUS, "package.installed");
    }
    
    /**
     * 
     * @param view
     */
    public final void packageuninstall(View view) {
        String pkgname = ((InputComponent)view.getElement("package")).get();
        PackageTool pkgtool = new PackageTool(this);
        
        pkgtool.uninstall(pkgname);

        view.setReloadableView(true);
        view.message(Const.STATUS, "package.uninstalled");
    }
    
    /**
     * 
     * @param view
     */
    public final void printinfo(View view) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        Map<String, String> links = view.getParameter("links");
        DocumentModel[] models = view.getParameter("models");
        Authorization[] authorizations = view.getParameter("authorizations");
        String[] numbers = view.getParameter("numbers");
        String[] dependencies = view.getParameter("dependencies");
        
        pagecontrol.add("back");
        
        if (authorizations.length > 0) {
            view.print("- Autorizações");
            for (Authorization authorization : authorizations)
                view.print(authorization.getName());
        }
        
        if (links.size() > 0) {
            view.print("\n- Links");
            for (String key : links.keySet())
                view.print(new StringBuilder(key).append(": ").
                        append(links.get(key)).toString());
        }
            
        if (models.length > 0) {
            view.print("\n- Modelos");
            for (DocumentModel model : models)
                view.print(model.getName());
        }
        
        if (numbers.length > 0) {
            view.print("\n- Objetos de numeração");
            for (String number : numbers)
                view.print(number);
        }
        
        if (view.getPrintLines().length == 0)
            view.print("Sem conteúdo instalável");
        
        if (dependencies != null) {
            view.print("\n- Dependências");
            for (String dependency : dependencies)
                view.print(dependency);
        }
        
        view.setTitle("package-contents");
    }
}
