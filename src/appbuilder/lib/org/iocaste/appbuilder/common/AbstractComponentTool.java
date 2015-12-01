package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.Element;

public abstract class AbstractComponentTool {
    protected ComponentEntry entry;
    
    public AbstractComponentTool(ComponentEntry entry) {
        this.entry = entry;
    }
    
    @SuppressWarnings("unchecked")
    protected final <T extends AbstractComponentData> T getComponentData() {
        return (T)entry.data;
    }
    
    protected final <T extends Element> T getElement(String name) {
        return entry.data.context.view.getElement(name);
    }
    
    public abstract void refresh();
    
    public abstract void run();
}
