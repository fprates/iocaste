package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ModelAdd extends AbstractCommand {
    
    public ModelAdd() {
        required("name");
        optional("table");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name;
        ExtendedObject object;
        ExtendedObject[] objects;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        objects = extcontext.project.getItems("model");
        object = readobjects(objects, "NAME", name);
        if (object != null) {
            message(Const.ERROR, "model %s already exists.", name);
            return;
        }
        
        object = extcontext.project.instance("model");
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("NAME", name);
        object.set("TABLE", parameters.get("table"));
        save("project", extcontext.project);
        print("data element %s updated.", name);
    }

}
