package org.iocaste.packagetool;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Response {
    
    public static final Map<String, String> init(Function function) {
        String[] packages = PackageTool.getAvailablePackages();
        PackageTool pkgtool = new PackageTool(function);
        Map<String, String> pkgsdata = new LinkedHashMap<>();
        
        for (String name : packages) {
            try {
                pkgtool.getInstallData(name);
            } catch (Exception e) {
                continue;
            }
            
            pkgsdata.put(name, pkgtool.isInstalled(name)?
                    "packageuninstall" : "packageinstall");
        }
        
        return pkgsdata;
    }
    
    /**
     * 
     * @param view
     * @param packages
     */
    public static final void main(View view, Map<String, String> packages) {
        Link link;
        TableItem item;
        String action;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        Parameter parameter = new Parameter(container, "package");
        Button info = new Button(container, "info");
        Table table = new Table(container, "packages");
        
        pagecontrol.add("home");
        table.setMark(true);
        table.setSelectionType(Table.SINGLE);
        
        new TableColumn(table, "name");
        new TableColumn(table, "action");
        
        for (String name : packages.keySet()) {
            action = packages.get(name);
            item = new TableItem(table);
            item.add(new Text(table, name));
            
            link = new Link(table, name, action);
            link.setText(action);
            link.add(parameter, name);
            item.add(link);
        }
        
        info.setVisible(table.length() > 0);
        view.setTitle("package-manager");
    }
    
    /**
     * 
     * @param view
     */
    public final static void printInfo(View view) {
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
