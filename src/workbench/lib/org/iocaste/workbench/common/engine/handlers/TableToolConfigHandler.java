package org.iocaste.workbench.common.engine.handlers;

import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.workbench.common.engine.Context;

public class TableToolConfigHandler extends AbstractConfigHandler {

    public TableToolConfigHandler(Context context) {
        super(context, ViewSpecItem.TYPES.TABLE_TOOL);
    }
    
    @Override
    public void set(String element, String name, Object value) {
        setTool(element, name, value);
    }
}
