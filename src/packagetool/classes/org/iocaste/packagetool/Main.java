package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.packagetool.services.IsInstalled;
import org.iocaste.protocol.Iocaste;

public class Main extends AbstractPageBuilder {
    
    public Main() {
        super();
        export("is_installed", new IsInstalled());
    }
    
    @Override
    public void config(PageBuilderContext context) throws Exception {
        Context extcontext;
        StandardPanel panel;
        
        extcontext = new Context(context);
        reload(context, extcontext);
        
        panel = new StandardPanel(context);
        panel.instance("main", new MainPanel(), extcontext);
        panel.instance("detail", new DetailPanel(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("PACKAGE", "iocaste-packagetool");
        defaultinstall.addToTaskGroup("ADMIN", "PACKAGE");
        defaultinstall.setProfile("ALL");
        defaultinstall.setProgramAuthorization("PACKAGE.EXECUTE");
        
        installObject("tasks", new TaskInstall());
        installObject("tasksgroups", new TasksGroupsInstall());
        installObject("usergroups", new UserTaskGroupsInstall());
        installObject("package", new PackageInstall());
        installObject("messages", new TextsInstall());
    }
    
    public static final void registerException(
            String pkgname, Context extcontext, Exception e) {
        Throwable cause;
        
        cause = e.getCause();
        extcontext.exceptions.put(pkgname, (cause == null)? e : cause);
    }
    
    private void reload(PageBuilderContext context, Context extcontext) {
        IsInstalled isinstalled;
        ExtendedObject object;
        DocumentModel model;
        String[] packages;
        PackageTool pkgtool;
         
        model = new Documents(context.function).getModel("PACKAGE_GRID");
        packages = new Iocaste(context.function).getAvailablePackages();
        pkgtool = new PackageTool(context.function);
        isinstalled = get("is_installed");
        
        for (String name : packages) {
            object = new ExtendedObject(model);
            object.set("NAME", name);
            
            try {
                pkgtool.getInstallData(name);
            } catch (Exception e) {
                registerException(name, extcontext, e);
                object.set("EXCEPTION", e.getMessage());
                extcontext.invalid.add(object);
                continue;
            }
            
            if (isinstalled.run(name))
                extcontext.installed.add(object);
            else
                extcontext.uninstalled.add(object);
        }
    }
}

class MainPanel extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        put("indetail", new DetailPackage("inpackages"));
        put("undetail", new DetailPackage("unpackages"));
        put("erdetail", new DetailPackage("erpackages"));
        put("install", new InstallPackage());
        put("remove", new UninstallPackage());
        put("update", new UpdatePackage());
    }
    
}

class DetailPanel extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new DetailSpec());
        set(new DetailInput());
    }
    
}