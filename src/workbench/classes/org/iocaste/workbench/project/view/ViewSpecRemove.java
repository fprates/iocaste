package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewSpecRemove extends AbstractCommand {
    
    public ViewSpecRemove(Context extcontext) {
        super("viewspec-remove", extcontext);
        required("name", null);
        checkview = true;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ExtendedObject specitem;
        Map<Object, ExtendedObject> specitems;
        Context extcontext = getExtendedContext();
        
        specitems = extcontext.view.getItemsMap("spec");
        name = parameters.get("name");
        specitem = specitems.get(name);
        if (specitem == null) {
            message(Const.ERROR, "invalid.specitem");
            return null;
        }
        
        extcontext.view.remove(specitem);
        save(extcontext.view);
        print("component %s removed.", name);
        return null;
    }
}
