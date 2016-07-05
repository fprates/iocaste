package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ModelItemAdd extends AbstractCommand {
    
    public ModelItemAdd(Context extcontext) {
        super("model-item-add", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        optionalbl("key", "KEY");
        optional("data-element", "DATA_ELEMENT");
        optional("field", "FIELD");
//        optional("reference", null);
        checkmodel = true;
        
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "model_item_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name, dtel, table, field;
        ExtendedObject object;
        ExtendedObject[] objects;
        Context extcontext = getExtendedContext();
        
        name = getst("name");
        objects = extcontext.model.getItems("item");
        object = readobjects(objects, "NAME", name);
        if (object != null) {
            message(Const.ERROR, "model.item.exists.");
            return null;
        }
        
        dtel = getst("data-element");
        if (dtel != null) {
            object = getObject("WB_DATA_ELEMENTS", dtel);
            if (object == null) {
                message(Const.ERROR, "invalid.dataelement", dtel);
                return null;
            }
        }
        
        field = getst("field");
        table = extcontext.model.getHeader().getst("TABLE");
        if ((field == null) && (table != null)) {
            message(Const.ERROR, "field.name.required");
            return null;
        }
        
        object = extcontext.model.instance("item", name);
        object.set("PROJECT", extcontext.project.getstKey());
        autoset(object);
        save(extcontext.model);
        message(Const.STATUS, "model.item.updated");
        return object;
    }

}
