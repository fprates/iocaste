package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ModelItemAdd extends AbstractCommand {
    
    public ModelItemAdd() {
        required("name");
        optional("key", "true", "false");
        optional("data-element");
        optional("field");
        optional("reference");
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
            message(Const.ERROR, "item %s for model %s already exists.", name);
            return;
        }
        
        object = extcontext.project.instance("model");
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("NAME", name);
        object.set("MODEL", extcontext.model.getstKey());
        object.set("NAME", parameters.get("name"));
        object.set("FIELD", parameters.get("field"));
        object.set("DATA_ELEMENT", parameters.get("data-element"));
        object.set("KEY", getBooleanParameter("key"));
        save("project", extcontext.project);
        print("model %s updated.", name);
    }

}
