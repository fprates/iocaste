package org.iocaste.packagetool;

import java.util.List;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.shell.common.Const;
import org.iocaste.tasksel.common.TaskSelector;

public class UpdatePackage extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        boolean ex;
        Context extcontext;
        String pkgname;
        List<ExtendedObject> packages;
        PackageTool pkgtool;
        
        packages = tableselectedget("inpackages");
        if (packages == null) {
            message(Const.STATUS, "no.packages.selected");
            return;
        }

        pkgtool = new PackageTool(context.function);
        extcontext = getExtendedContext();
        ex = false;
        for (ExtendedObject object : packages) {
            pkgname = object.getst("NAME");
            try {
                pkgtool.update(pkgname);
                extcontext.uninstalled.add(object);
                object = readobjects(extcontext.installed, "NAME", pkgname);
                extcontext.installed.remove(object);
            } catch (Exception e) {
                object = readobjects(extcontext.installed, "NAME", pkgname);
                object.set("EXCEPTION", e.getMessage());
                ex = true;
            }
        }
        
        new TaskSelector(context.function).refresh();
        if (ex)
            message(Const.WARNING, "some.packages.failed");
        else
            message(Const.STATUS, "package.uninstalled");
        inputrefresh();
    }

}
