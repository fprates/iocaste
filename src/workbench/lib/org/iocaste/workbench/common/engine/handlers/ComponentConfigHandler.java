package org.iocaste.workbench.common.engine.handlers;

import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.workbench.common.engine.Context;

public class ComponentConfigHandler extends AbstractConfigHandler {

    public ComponentConfigHandler(Context context, ViewSpecItem.TYPES type) {
        super(context, type);
    }
    
    @Override
    public void set(String element, String name, Object value) {
        ControlComponent control = getElement(element);
        super.set(element, name, value);
        switch (name) {
        case "action":
            control.setAction((String)value);
            break;
        case "cancelable":
            control.setCancellable((boolean)value);
            break;
        case "nolockscreen":
            control.setNoScreenLock((boolean)value);
            break;
        }
    }
}
