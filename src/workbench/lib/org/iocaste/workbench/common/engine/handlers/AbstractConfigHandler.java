package org.iocaste.workbench.common.engine.handlers;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.Element;
import org.iocaste.workbench.common.engine.Context;

public abstract class AbstractConfigHandler implements ElementConfigHandler {
    private Context extcontext;
    
    public AbstractConfigHandler(Context extcontext, ViewSpecItem.TYPES type) {
        extcontext.config.handlers.put(type.toString(), this);
        this.extcontext = extcontext;
    }
    
    protected <T extends Element> T getElement(String name) {
        return extcontext.getContext().view.getElement(name);
    }
    
    protected <T extends AbstractComponentData> T getTool(String name) {
        return extcontext.getContext().getView().getComponents().
                getComponentData(name);
    }
    
    @Override
    public void set(String elementname, String name, Object value) {
        Element element = getElement(elementname);
        switch (name) {
        case "disabled":
            element.setEnabled(!(boolean)value);
            break;
        case "style":
            element.setStyleClass((String)value);
            break;
        case "visible":
            element.setVisible(!(boolean)value);
            break;
        }
    }
    
    protected final void setTool(String element, String name, Object value) {
        AbstractComponentData data= getTool(element);
        
        switch (name) {
        case "model":
            data.model = (String)value;
            break;
        case "disabled":
            data.disabled = (boolean)value;
            break;
        case "style":
            data.style = (String)value;
            break;
        }
    }
}