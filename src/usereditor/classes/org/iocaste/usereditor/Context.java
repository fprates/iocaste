package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.PackageTool;

public class Context extends AbstractExtendedContext {
    public UserData userdata;
    public PackageTool pkgtool;
    public ExtendedObject extras;
    
    public Context(PageBuilderContext context) {
        super(context);
        userdata = new UserData();
        pkgtool = new PackageTool(context.function);
    }
}
