package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.view.config.ViewElementAttribute;

public class ViewConfigEdit extends AbstractCommand {
    
    public ViewConfigEdit() {
        required("element");
        checkparameters = false;
        checkview = true;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String element, type;
        ExtendedObject object, config;
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
            config = attribute.instance(object, parameters.get(key));
            print(config.toString());
        }
        save(extcontext.view);
        print("viewconfig for spec item %s added.", element);
        return null;
    }
}
