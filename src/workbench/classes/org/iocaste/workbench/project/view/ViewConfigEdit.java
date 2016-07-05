package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.view.config.ViewConfigItemUpdate;
import org.iocaste.workbench.project.view.config.ViewElementAttribute;

public class ViewConfigEdit extends AbstractCommand {
    
    public ViewConfigEdit(Context extcontext) {
        super("viewconfig", extcontext);
        ActionContext actionctx;
        
        required("element", null);
        checkparameters = false;
        checkview = true;
        
        actionctx = getActionContext();
        actionctx.updateviewer = new ViewConfigItemUpdate(extcontext);
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String element, type;
        ExtendedObject object, result;
        ViewElementAttribute attribute;
        Map<String, ViewElementAttribute> attributes;
        Context extcontext = getExtendedContext();
        
        element = getst("element");
        object = extcontext.view.getItemsMap("spec").get(element);
        if (object == null) {
            message(Const.ERROR, "invalid.view.element");
            return null;
        }
        
        result = null;
        type = object.getst("TYPE");
        attributes = extcontext.viewconfig.attribs.get(type);
        for (String key : getKeys()) {
            if (key.equals("element"))
                continue;
            attribute = attributes.get(key);
            if (attribute == null) {
                message(Const.ERROR, "invalid.view.element.attribute");
                return null;
            }
            result = attribute.instance(object, getst(key));
        }
        save(extcontext.view);
        message(Const.STATUS, "viewconfig.added");
        return result;
    }
}
