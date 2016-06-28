package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewSpecRemove extends AbstractCommand {
    
    public ViewSpecRemove() {
        required("name");
        checkview = true;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name;
        ExtendedObject specitem;
        Context extcontext = getExtendedContext();
        Map<Object, ExtendedObject> specitems = extcontext.view.getItemsMap("spec");
        
        name = parameters.get("name");
        specitem = specitems.get(name);
        if (specitem == null) {
            message(Const.ERROR, "invalid.specitem");
            return;
        }
        
        extcontext.view.remove(specitem);
        save(extcontext.view);
        print("component %s removed.", name);
    }
}
