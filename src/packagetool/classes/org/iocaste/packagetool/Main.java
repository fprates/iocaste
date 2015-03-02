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
    
    private void reload(PageBuilderContext context, Context extcontext) {
        ExtendedObject object;
        DocumentModel model;
        List<String> packages;
        PackageTool pkgtool;
        
        model = new Documents(context.function).getModel("PACKAGE");
        packages = PackageTool.getAvailablePackages();
        pkgtool = new PackageTool(context.function);
        
        for (String name : packages) {
            object = new ExtendedObject(model);
            object.set("NAME", name);
            
            try {
                pkgtool.getInstallData(name);
            } catch (Exception e) {
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
