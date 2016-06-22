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
        
        checkmodel = true;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name, model, dtel;
        ExtendedObject object;
        ExtendedObject[] objects;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        model = extcontext.model.getstKey();
        objects = extcontext.model.getItems("item");
        object = readobjects(objects, "NAME", name);
        if (object != null) {
            message(Const.ERROR, "item %s for model %s already exists.",
                    name, model);
            return;
        }
        
        dtel = parameters.get("data-element");
        if (dtel != null) {
            object = getObject("WB_DATA_ELEMENTS", dtel);
            if (object == null) {
                message(Const.ERROR, "data element %s invalid.", dtel);
                return;
            }
        }
        
        object = extcontext.model.instance("item");
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("NAME", name);
        object.set("MODEL", model);
        object.set("NAME", parameters.get("name"));
        object.set("FIELD", parameters.get("field"));
        object.set("DATA_ELEMENT", dtel);
        object.set("KEY", getBooleanParameter("key"));
        save("model", extcontext.model);
        print("model item %s updated.", name);
    }

}
