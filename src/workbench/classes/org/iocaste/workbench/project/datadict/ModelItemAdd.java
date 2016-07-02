package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ModelItemAdd extends AbstractCommand {
    
    public ModelItemAdd(Context extcontext) {
        super("model-item-add", extcontext);
        required("name", "NAME");
        optionalbl("key", "KEY");
        optional("data-element", "DATA_ELEMENT");
        optional("field", "FIELD");
//        optional("reference", null);
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
        autoset(object);
        save(extcontext.model);
        print("model item %s updated.", name);
        return object;
    }

}
