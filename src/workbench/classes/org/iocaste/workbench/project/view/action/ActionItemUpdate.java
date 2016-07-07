package org.iocaste.workbench.project.view.action;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.CommandArgument;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerUpdate;

public class ActionItemUpdate implements ViewerUpdate {
    private Context extcontext;
    
    public ActionItemUpdate(Context extcontext) {
        this.extcontext = extcontext;
    }
    
    @Override
    public void add(Object object) {
        extcontext.add("actions_items", (ExtendedObject)object);
    }

    private CommandArgument argument(String name) {
        return new CommandArgument(AbstractCommand.OPTIONAL, name);
    }

    @Override
    public void preexecute(ActionContext actionctx, ExtendedObject object) {
        actionctx.arguments.clear();
        actionctx.arguments.put("name", argument("NAME"));
        actionctx.arguments.put("class", argument("CLASS"));
        actionctx.arguments.put("type", argument("TYPE"));
    }
    
    @Override
    public void remove(Object object) { }
    
}