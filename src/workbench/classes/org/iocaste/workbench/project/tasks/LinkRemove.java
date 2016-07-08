package org.iocaste.workbench.project.tasks;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class LinkRemove extends AbstractCommand {

    public LinkRemove(Context extcontext) {
        super("link-remove", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "links_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ExtendedObject object;
        Context extcontext = getExtendedContext();

        name = getst("name");
        object = extcontext.project.getItemsMap("link").get(name);
        if (object == null) {
            message(Const.ERROR, "invalid.link");
            return null;
        }
        
        extcontext.project.remove(object);
        save(extcontext.project);
        message(Const.STATUS, "link.removed");
        return null;
    }

}
