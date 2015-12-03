package org.iocaste.packagetool;

import java.util.List;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.shell.common.Const;
import org.iocaste.tasksel.common.TaskSelector;

public class UninstallPackage extends AbstractActionHandler {

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
                pkgtool.uninstall(pkgname);
                extcontext.uninstalled.add(object);
                object = readobjects(extcontext.installed, "NAME", pkgname);
                extcontext.installed.remove(object);
            } catch (Exception e) {
                storeException(e, pkgname, extcontext, extcontext.installed,
                        object);
                ex = true;
            }
        }
        
        new TaskSelector(context.function).refresh();
        if (ex)
            message(Const.WARNING, "some.packages.failed");
        else
            message(Const.STATUS, "package.uninstalled");
    }

    public static final void storeException(Exception e, String pkgname,
            Context extcontext, Set<ExtendedObject> items, ExtendedObject object) {
        Throwable cause;
        
        cause = e.getCause();
        extcontext.exceptions.put(pkgname, (cause == null)? e : cause);
        object = readobjects(items, "NAME", pkgname);
        object.set("EXCEPTION", e.getMessage());
    }
}
