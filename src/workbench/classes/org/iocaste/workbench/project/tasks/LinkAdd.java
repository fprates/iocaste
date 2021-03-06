package org.iocaste.workbench.project.tasks;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class LinkAdd extends AbstractCommand {

    public LinkAdd(Context extcontext) {
        super("link-add", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        required("command", "COMMAND");
        optional("group", "GROUP");
        
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "links_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ExtendedObject object;
        ExtendedObject[] objects;
        Context extcontext = getExtendedContext();

        name = getst("name");
        objects = extcontext.project.getItems("link");
        object = readobjects(objects, "NAME", name);
        if (object != null) {
            message(Const.ERROR, "link.exists");
            return null;
        }
        
        object = extcontext.project.instance("link", name);
        object.set("PROJECT", extcontext.project.getstKey());
        autoset(object);
        save(extcontext.project);
        message(Const.STATUS, "link.updated");
        return object;
    }

}
