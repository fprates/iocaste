package org.iocaste.workbench.project.view.config;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.CommandArgument;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerUpdate;

public class ViewConfigItemUpdate implements ViewerUpdate {
    private Context extcontext;
    
    public ViewConfigItemUpdate(Context extcontext) {
        this.extcontext = extcontext;
    }
    
    @Override
    public void add(Object object) {
        extcontext.add("spec_config_items", (ExtendedObject)object);
    }

    private CommandArgument argument(String name) {
        return new CommandArgument(AbstractCommand.OPTIONAL, name);
    }

    @Override
    public void preexecute(ActionContext actionctx, ExtendedObject object) {
        object.set("SPEC", extcontext.spec.getstKey());
        actionctx.arguments.clear();
        actionctx.arguments.put(object.getst("NAME"), argument("VALUE"));
        actionctx.arguments.put("element", argument("SPEC"));
    }
    
    @Override
    public void remove(Object object) {
        extcontext.remove("spec_config_items", (ExtendedObject)object);
    }
    
}