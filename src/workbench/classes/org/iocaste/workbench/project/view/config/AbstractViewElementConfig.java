package org.iocaste.workbench.project.view.config;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.ViewSpecItem;

public abstract class AbstractViewElementConfig {
    private Map<String, ViewElementAttribute> attributes;
    private ViewConfigContext context;
    
    public AbstractViewElementConfig(
            ViewConfigContext context, ViewSpecItem.TYPES type) {
        attributes = new HashMap<>();
        this.context = context;
        context.attribs.put(type.toString(), attributes);
    }
    
    protected final void add(ViewElementAttribute attribute) {
        attributes.put(attribute.getName(), attribute);
        attribute.setContext(context.extcontext);
    }
}
