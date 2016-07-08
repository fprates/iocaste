package org.iocaste.workbench.project.view.config;

import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.DataType;

public class InputComponentConfig extends AbstractViewElementConfig {
    
    public InputComponentConfig(
            ViewConfigContext context, ViewSpecItem.TYPES type) {
        super(context, type);
        add(new GenericElementAttribute("disabled", DataType.BOOLEAN));
        add(new GenericElementAttribute("required", DataType.BOOLEAN));
        add(new GenericElementAttribute("secret", DataType.BOOLEAN));
    }
}

class GenericElementAttribute extends AbstractViewElementAttribute {

    public GenericElementAttribute(String name, int type) {
        super(name, type);
    }
}