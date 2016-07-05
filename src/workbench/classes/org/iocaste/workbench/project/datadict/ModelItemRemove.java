package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ModelItemRemove extends AbstractCommand {
    
    public ModelItemRemove(Context extcontext) {
        super("model-item-remove", extcontext);
        required("name", null);
        checkmodel = true;
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
        return null;
    }

}
