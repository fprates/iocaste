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
    protected Object entry(PageBuilderContext context) {
        String name, model, dtel, table, field;
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
            return null;
        }
        
        dtel = parameters.get("data-element");
        if (dtel != null) {
            object = getObject("WB_DATA_ELEMENTS", dtel);
            if (object == null) {
                message(Const.ERROR, "data element %s invalid.", dtel);
                return null;
            }
        }
        
        field = parameters.get("field");
        table = extcontext.model.getHeader().getst("TABLE");
        if ((field == null) && (table != null)) {
            message(Const.ERROR, "field.name.required");
            return null;
        }
        
        object = extcontext.model.instance("item", name);
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("NAME", name);
        object.set("MODEL", model);
        object.set("FIELD", field);
        object.set("DATA_ELEMENT", dtel);
        object.set("KEY", getBooleanParameter("key"));
        save(extcontext.model);
        print("model item %s updated.", name);
        return object;
    }

}
