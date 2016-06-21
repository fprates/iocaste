package org.iocaste.workbench.project.tasks;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class LinkAdd extends AbstractCommand {

    public LinkAdd() {
        required("name");
        required("program");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name, program;
        ExtendedObject object;
        Context extcontext = getExtendedContext();
        
        if (extcontext.project == null) {
            message(Const.ERROR, "undefined.project");
            return;
        }

        name = parameters.get("name");
        program = parameters.get("program");
        
        object = extcontext.project.instance("link");
        object.set("NAME", name);
        object.set("COMMAND", program);
        object.set("PROJECT", extcontext.project.getstKey());
        save("project", extcontext.project);
        extcontext.output.add(String.format("link %s created.", name));
    }

}
