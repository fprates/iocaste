package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ModelItemRemove extends AbstractCommand {
    
    public ModelItemRemove(Context extcontext) {
        super("model-item-remove", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        checkmodel = true;
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "model_item_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ExtendedObject object;
        Context extcontext = getExtendedContext();
        
        name = getst("name");
        object = extcontext.model.getItemsMap("item").get(name);
        if (object == null) {
            message(Const.ERROR, "invalid.model.item");
            return null;
        }
        
        extcontext.model.remove(object);
        save(extcontext.model);
        message(Const.STATUS, "model.item.removed.");
        return object;
    }

}
