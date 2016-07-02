package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewSpecAdd extends AbstractCommand {
    private ViewSpecItem.TYPES type;
    
    public ViewSpecAdd(String name, Context extcontext, ViewSpecItem.TYPES type)
    {
        super(name, extcontext);
        this.type = type;
        required("name", "NAME");
        optional("parent", "PARENT");
        checkview = true;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ExtendedObject object;
        String name, parent;
        Context extcontext = getExtendedContext();
        Map<Object, ExtendedObject> specitems =
                extcontext.view.getItemsMap("spec");
        
        parent = parameters.get("parent");
        object = specitems.get(parent);
        if ((parent != null) && (object == null)) {
            message(Const.ERROR, "invalid.parent");
            return null;
        }
        
        name = parameters.get("name");
        object = specitems.get(name);
        if (object != null) {
            message(Const.ERROR, "specitem.already.exists");
            return null;
        }
        
        object = extcontext.view.instance("spec", name);
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("SCREEN", extcontext.view.getstKey());
        object.set("TYPE", type.toString());
        autoset(object);
        save(extcontext.view);
        print("%s %s added.", type.toString(), name);
        return object;
    }
}
