package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.Element;

public abstract class AbstractComponentTool {
    private String htmlname;
    protected ComponentEntry entry;
    
    public AbstractComponentTool(ComponentEntry entry) {
        this.entry = entry;
    }
    
    @SuppressWarnings("unchecked")
    protected final <T extends AbstractComponentData> T getComponentData() {
        return (T)entry.data;
    }
    
    public final <T extends Element> T getElement() {
        return getElement(htmlname);
    }
    
    protected final <T extends Element> T getElement(String name) {
        return entry.data.context.view.getElement(name);
    }
    
    protected final String getHtmlName() {
        return htmlname;
    }
    
    public String getNSField() {
        return null;
    }
    
    public void load(AbstractComponentData data) { }
    
    public abstract void refresh();
    
    public abstract void run();
    
    protected final void setHtmlName(String htmlname) {
        this.htmlname = htmlname;
    }
}
