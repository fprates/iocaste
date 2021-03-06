package org.iocaste.workbench.common.engine.handlers;

import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.workbench.common.engine.Context;

public class DataFormConfigHandler extends AbstractConfigHandler {

    public DataFormConfigHandler(Context context) {
        super(context, ViewSpecItem.TYPES.DATA_FORM);
    }
    
    @Override
    public void set(String element, String name, Object value) {
        setTool(element, name, value);
    }
}
