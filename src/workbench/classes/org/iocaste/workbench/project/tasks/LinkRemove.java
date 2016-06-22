package org.iocaste.workbench.project.tasks;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class LinkRemove extends AbstractCommand {

    public LinkRemove() {
        required("name");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name;
        ExtendedObject object;
        ExtendedObject[] objects;
        Context extcontext = getExtendedContext();

        name = parameters.get("name");
        objects = extcontext.project.getItems("link");
        object = readobjects(objects, "NAME", name);
        if (object == null) {
            message(Const.ERROR, "link %s doesn't exist.", name);
            return;
        }
        
        extcontext.project.remove("link");
        for (ExtendedObject link : objects)
            if (!link.getst("NAME").equals(name))
                extcontext.project.add(link);
        save("project", extcontext.project);
        print("link %s removed", name);
    }

}
