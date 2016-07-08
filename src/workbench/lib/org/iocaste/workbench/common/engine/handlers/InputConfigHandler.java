package org.iocaste.workbench.common.engine.handlers;

import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.workbench.common.engine.Context;

public class InputConfigHandler extends AbstractConfigHandler {

    public InputConfigHandler(Context context, ViewSpecItem.TYPES type) {
        super(context, type);
    }
    
    @Override
    public void set(String element, String name, Object value) {
        InputComponent input = getElement(element);
        super.set(element, name, value);
        switch (name) {
        case "vlength":
            input.setVisibleLength((int)value);
            break;
        case "required":
            input.setObligatory((boolean)value);
            break;
        case "secret":
            input.setSecret((boolean)value);
            break;
        }
    }
}
