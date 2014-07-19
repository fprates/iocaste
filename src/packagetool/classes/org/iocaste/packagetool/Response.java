package org.iocaste.packagetool;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;

public class Response {
    
    public static final void init(Context context) {
        String[] packages = PackageTool.getAvailablePackages();
        PackageTool pkgtool = new PackageTool(context.function);
        
        context.pkgsdata = new LinkedHashMap<>();
        for (String name : packages) {
            try {
                pkgtool.getInstallData(name);
            } catch (Exception e) {
                continue;
            }
            
            context.pkgsdata.put(name, pkgtool.isInstalled(name)?
                    "packageuninstall" : "packageinstall");
        }
    }
    
    /**
     * 
     * @param view
     * @param packages
     */
    public static final void main(Context context) {
        Link link;
        TableItem item;
        String action;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        Table table = new Table(container, "packages");
        
        pagecontrol.add("home");

        table.setMark(true);
        table.setSelectionType(Table.SINGLE);
        
        new TableColumn(table, "name");
        new TableColumn(table, "action");
        
        for (String name : context.pkgsdata.keySet()) {
            action = context.pkgsdata.get(name);
            item = new TableItem(table);
            new Text(item, name.concat("_tx")).setText(name);
            
            link = new Link(item, name, action);
            link.setText(action);
            link.add("package", name);
        }

        if (table.size() > 0)
            pagecontrol.add("info", PageControl.REQUEST);
        
        context.view.setTitle("package-manager");
    }
    
    /**
     * 
     * @param view
     * @param data
     */
    public final static void printInfo(Context context) {
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        Map<String, String> links = context.data.getLinks();
        Map<String, DocumentModel> models = context.data.getModels();
        Authorization[] authorizations = context.data.getAuthorizations();
        String[] numbers = context.data.getNumberFactories();
        String[] dependencies = context.data.getDependencies();
        
        pagecontrol.add("back");
        
        if (authorizations.length > 0) {
            context.view.print("- Autorizações");
            for (Authorization authorization : authorizations)
                context.view.print(authorization.getName());
        }
        
        if (links.size() > 0) {
            context.view.print("\n- Links");
            for (String key : links.keySet())
                context.view.print(new StringBuilder(key).append(": ").
                        append(links.get(key)).toString());
        }
            
        if (models.size() > 0) {
            context.view.print("\n- Modelos");
            for (DocumentModel model : models.values())
                context.view.print(model.getName());
        }
        
        if (numbers.length > 0) {
            context.view.print("\n- Objetos de numeração");
            for (String number : numbers)
                context.view.print(number);
        }
        
        if (context.view.getPrintLines().length == 0)
            context.view.print("Sem conteúdo instalável");
        
        if (dependencies != null) {
            context.view.print("\n- Dependências");
            for (String dependency : dependencies)
                context.view.print(dependency);
        }
        
        context.view.setTitle("package-contents");
    }

}
