package org.iocaste.kernel.runtime.shell;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.tooldata.ToolData;

public abstract class AbstractComponentTool {
    private String htmlname;
    protected ComponentEntry entry;
    protected ViewContext viewctx;
    
    public AbstractComponentTool(ViewContext viewctx, ComponentEntry entry) {
        this.entry = entry;
        this.viewctx = viewctx;
    }
    
    protected final ToolData getComponentData() {
        return entry.data;
    }
    
    public final <T extends Element> T getElement() {
        return getElement(htmlname);
    }
    
    protected final <T extends Element> T getElement(String name) {
        return viewctx.view.getElement(name);
    }
    
    protected final String getHtmlName() {
        return htmlname;
    }
    
    public String getNSField() {
        return null;
    }
    
    public abstract void load();
    
    public abstract void refresh();
    
    public abstract void run() throws Exception;
    
    protected final void setHtmlName(String htmlname) {
        this.htmlname = htmlname;
    }
}
