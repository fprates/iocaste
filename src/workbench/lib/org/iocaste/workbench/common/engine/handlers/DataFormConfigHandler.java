package org.iocaste.workbench.common.engine.handlers;

import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.workbench.common.engine.Context;

public class DataFormConfigHandler extends AbstractConfigHandler {

    public DataFormConfigHandler(Context context) {
        super(context, ViewSpecItem.TYPES.DATA_FORM);
    }
    
    @Override
    public void set(String element, String name, Object value) {
        DataFormToolData dataform = getTool(element);
        
        switch (name) {
        case "model":
            dataform.model = (String)value;
            break;
        }
    }
}
