package org.iocaste.workbench.project.tasks;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class LinkAdd extends AbstractCommand {

    public LinkAdd(Context extcontext) {
        super("link-add", extcontext);
        required("name", "NAME");
        required("command", "COMMAND");
        optional("group", "GROUP");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ExtendedObject object;
        ExtendedObject[] objects;
        Context extcontext = getExtendedContext();

        name = parameters.get("name");
        objects = extcontext.project.getItems("link");
        object = readobjects(objects, "NAME", name);
        if (object != null) {
            message(Const.ERROR, "link %s already exists.", name);
            return null;
        }
        
        object = extcontext.project.instance("link", name);
        object.set("PROJECT", extcontext.project.getstKey());
        autoset(object);
        save(extcontext.project);
        print("link %s updated.", name);
        return object;
    }

}
