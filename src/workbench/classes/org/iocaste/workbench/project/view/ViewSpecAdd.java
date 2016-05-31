package org.iocaste.workbench.project.view;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewSpecAdd extends AbstractCommand {
    private ViewSpecItem.TYPES type;
    
    public ViewSpecAdd(ViewSpecItem.TYPES type) {
        this.type = type;
        required("view");
        required("name");
        optional("parent");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ExtendedObject object, view;
        String name, parent, viewname;
        Context extcontext = getExtendedContext();
        
        if (extcontext.project == null) {
            message(Const.ERROR, "undefined.project");
            return;
        }
        
        viewname = parameters.get("view");
        view = ViewAdd.getView(extcontext.project, viewname);
        if (view == null) {
            message(Const.ERROR, "invalid.view");
            return;
        }
        
        parent = parameters.get("parent");
        if (parent != null)
            if (getSpecItem(extcontext.project, view, parent) == null) {
                message(Const.ERROR, "invalid.parent");
                return;
            }
        
        name = parameters.get("name");
        if (getSpecItem(extcontext.project, view, name) != null) {
            message(Const.ERROR, "specitem.already.exists");
            return;
        }
        
        object = instance("WB_SCREEN_SPEC_ITEMS");
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("SCREEN", view.getst("PROJECT_SCREEN"));
        object.set("NAME", name);
        object.set("PARENT", parent);
        object.set("TYPE", type.toString());
        extcontext.project.add(object);
        save("project", extcontext.project);
    }

    private ExtendedObject getSpecItem(
            ComplexDocument project, ExtendedObject view, String name) {
        ExtendedObject[] objects;
        Map<String, Object> criteria;
        
        objects = project.getItems("screen_spec_item");
        if ((objects == null) || (objects.length == 0))
            return null;
        criteria = new HashMap<>();
        criteria.put("SCREEN", view.getst("PROJECT_SCREEN"));
        criteria.put("PROJECT", project.getstKey());
        criteria.put("NAME", name);
        return readobjects(objects, criteria);
    }
}
