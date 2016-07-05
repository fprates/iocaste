package org.iocaste.workbench.project.java;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class PackageAdd extends AbstractCommand {

    public PackageAdd(Context extcontext) {
        super("package-add", extcontext);
        ActionContext actionctx;
        
        required("name", "PACKAGE");
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "packages_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ExtendedObject object;
        String name;
        ComplexDocument _package;
        Context extcontext;
        
        name = getst("name");
        extcontext = getExtendedContext();
        _package = extcontext.project.getDocumentsMap("class").get(name);
        if (_package != null) {
            message(Const.ERROR, "package.exists");
            return null;
        }
        
        _package = extcontext.project.instance("class", name);
        object = _package.getHeader();
        object.set("PROJECT", extcontext.project.getstKey());
        autoset(object);
        save(_package);
        message(Const.STATUS, "added.package");
        return object;
    }

}
