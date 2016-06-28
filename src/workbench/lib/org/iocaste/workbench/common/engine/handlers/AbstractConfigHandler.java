package org.iocaste.workbench.common.engine.handlers;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.workbench.common.engine.Context;

public abstract class AbstractConfigHandler implements ElementConfigHandler {
    private Context extcontext;
    
    public AbstractConfigHandler(Context extcontext, ViewSpecItem.TYPES type) {
        extcontext.config.handlers.put(type.toString(), this);
        this.extcontext = extcontext;
    }
    
    protected <T extends AbstractComponentData> T getTool(String name) {
        return extcontext.getContext().getView().getComponents().
                getComponentData(name);
    }
}