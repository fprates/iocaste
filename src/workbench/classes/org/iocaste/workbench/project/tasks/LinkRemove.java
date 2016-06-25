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
        Context extcontext = getExtendedContext();

        name = parameters.get("name");
        object = extcontext.project.getItemsMap("link", "NAME").get(name);
        if (object == null) {
            message(Const.ERROR, "link %s doesn't exist.", name);
            return;
        }
        
        extcontext.project.remove(object);
        save(extcontext.project);
        print("link %s removed", name);
    }

}
