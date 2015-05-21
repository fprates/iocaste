package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.packagetool.common.PackageTool;

public class Context implements ExtendedContext {
    public UserData userdata;
    public PackageTool pkgtool;
    
    public Context(PageBuilderContext context) {
        userdata = new UserData();
        pkgtool = new PackageTool(context.function);
    }
}
