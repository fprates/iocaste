package org.iocaste.workbench.project.tasks;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class LinkAdd extends AbstractCommand {

    public LinkAdd() {
        required("name");
        required("command");
        optional("group");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name, command, group;
        ExtendedObject object;
        ExtendedObject[] objects;
        Context extcontext = getExtendedContext();

        name = parameters.get("name");
        command = parameters.get("command");
        group = parameters.get("group");
        
        objects = extcontext.project.getItems("link");
        object = readobjects(objects, "NAME", name);
        if (object != null) {
            message(Const.ERROR, "link %s already exists.", name);
            return;
        }
        
        object = extcontext.project.instance("link");
        object.set("NAME", name);
        object.set("COMMAND", command);
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("GROUP", group);
        save("project", extcontext.project);
        print("link %s updated.", name);
    }

}
