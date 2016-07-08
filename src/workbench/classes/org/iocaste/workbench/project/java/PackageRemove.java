package org.iocaste.workbench.project.java;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class PackageRemove extends AbstractCommand {

    public PackageRemove(Context extcontext) {
        super("package-remove", extcontext);
        ActionContext actionctx;
        
        required("name", "PACKAGE");
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "packages_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ComplexDocument _package;
        Context extcontext;
        
        name = getst("name");
        extcontext = getExtendedContext();
        _package = extcontext.project.getDocumentsMap("class").get(name);
        if (_package == null) {
            message(Const.ERROR, "invalid.package");
            return null;
        }
        
        if (_package.getItemsMap("class").size() > 0) {
            message(Const.ERROR, "package.is.not.empty");
            return null;
        }
        
        extcontext.project.remove(_package);
        delete(_package);
        message(Const.STATUS, "package.removed");
        return null;
    }

}
