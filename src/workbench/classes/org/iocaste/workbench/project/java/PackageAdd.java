package org.iocaste.workbench.project.java;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class PackageAdd extends AbstractCommand {

    public PackageAdd() {
        required("name");
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ExtendedObject object;
        String name;
        ComplexDocument _package;
        Context extcontext;
        
        name = parameters.get("name");
        extcontext = getExtendedContext();
        _package = extcontext.project.getDocumentsMap("class").get(name);
        if (_package != null) {
            message(Const.ERROR, "package %s already exists.");
            return;
        }
        
        _package = extcontext.project.instance("class");
        object = _package.getHeader();
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("PACKAGE", name);
        save(_package);
        print("package %s added.", name);
    }

}
