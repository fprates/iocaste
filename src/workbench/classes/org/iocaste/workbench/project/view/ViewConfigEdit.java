package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.view.config.ViewElementAttribute;

public class ViewConfigEdit extends AbstractCommand {
    
    public ViewConfigEdit(Context extcontext) {
        super("viewconfig", extcontext);
        required("element", null);
        checkparameters = false;
        checkview = true;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String element, type;
        ExtendedObject object;
        ViewElementAttribute attribute;
        Map<String, ViewElementAttribute> attributes;
        Context extcontext = getExtendedContext();
        
        element = parameters.get("element");
        object = extcontext.view.getItemsMap("spec").get(element);
        if (object == null) {
            message(Const.ERROR, "invalid.view.element");
            return null;
        }
        
        type = object.getst("TYPE");
        attributes = extcontext.viewconfig.attribs.get(type);
        for (String key : parameters.keySet()) {
            if (key.equals("element"))
                continue;
            attribute = attributes.get(key);
            if (attribute == null) {
                message(Const.ERROR, "invalid.view.element.attribute");
                return null;
            }
            attribute.instance(object, parameters.get(key));
        }
        save(extcontext.view);
        message(Const.STATUS, "viewconfig.added");
        return null;
    }
}
