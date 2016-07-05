package org.iocaste.workbench.project.java;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.CommandArgument;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerUpdate;

public class ClassItemUpdate implements ViewerUpdate {
    private Context extcontext;
    
    public ClassItemUpdate(Context extcontext) {
        this.extcontext = extcontext;
    }

    private CommandArgument argument(String name) {
        return new CommandArgument(AbstractCommand.OPTIONAL, name);
    }
    
    @Override
    public void postexecute(Object object) {
        extcontext.getContext().function.dontPushPage();
    }

    @Override
    public void preexecute(ActionContext actionctx, ExtendedObject object) {
        object.set("PACKAGE", extcontext.pkgitem.getstKey());
        actionctx.arguments.clear();
        actionctx.arguments.put("package", argument("PACKAGE"));
        actionctx.arguments.put("class", argument("NAME"));
    }
    
}