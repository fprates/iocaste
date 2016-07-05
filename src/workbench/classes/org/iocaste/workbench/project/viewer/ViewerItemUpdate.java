package org.iocaste.workbench.project.viewer;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.CommandArgument;
import org.iocaste.workbench.Context;

public class ViewerItemUpdate implements ViewerUpdate {
    private String items;
    private Context extcontext;
    
    public ViewerItemUpdate(Context extcontext, String items) {
        this.items = items;
        this.extcontext = extcontext;
    }

    private CommandArgument argument(String name) {
        return new CommandArgument(AbstractCommand.OPTIONAL, name);
    }
    
    @Override
    public void postexecute(Object object) {
        extcontext.add(items, (ExtendedObject)object);
    }

    @Override
    public void preexecute(ActionContext actionctx, ExtendedObject object) {
        switch (actionctx.name) {
        case "viewconfig":
            object.set("SPEC", extcontext.specitem.getst("NAME"));
            actionctx.arguments.clear();
            actionctx.arguments.put(object.getst("NAME"), argument("VALUE"));
            actionctx.arguments.put("element", argument("SPEC"));
            break;
        }
        
    }
    
}