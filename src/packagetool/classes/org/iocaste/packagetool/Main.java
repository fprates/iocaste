package org.iocaste.packagetool;

import java.util.List;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.PackageTool;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        ViewContext view;
        Context extcontext;
        
        extcontext = new Context();
        reload(context, extcontext);
        
        view = context.instance("main");
        view.set(extcontext);
        view.set(new MainSpec());
        view.set(new MainConfig());
        view.set(new MainInput());
        view.put("indetail", new DetailPackage("inpackages"));
        view.put("undetail", new DetailPackage("unpackages"));
        view.put("erdetail", new DetailPackage("erpackages"));
        view.put("install", new InstallPackage());
        view.put("remove", new UninstallPackage());
        view.put("update", new UpdatePackage());
        
        view = context.instance("detail");
        view.set(extcontext);
        view.set(new DetailSpec());
        view.set(new DetailInput());
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
        ExtendedObject object;
        DocumentModel model;
        List<String> packages;
        PackageTool pkgtool;
        
        model = new Documents(context.function).getModel("PACKAGE_GRID");
        packages = PackageTool.getAvailablePackages();
        pkgtool = new PackageTool(context.function);
        
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
            
            if (pkgtool.isInstalled(name))
                extcontext.installed.add(object);
            else
                extcontext.uninstalled.add(object);
        }
    }
}
