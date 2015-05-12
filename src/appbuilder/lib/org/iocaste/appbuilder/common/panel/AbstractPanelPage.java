package org.iocaste.appbuilder.common.panel;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;

public abstract class AbstractPanelPage {
    private PageBuilderContext context;
    public Map<String, PanelPageItem> items;
    
    public AbstractPanelPage() {
        items = new LinkedHashMap<>();
    }
    public abstract void execute();
    
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return context.getView().getExtendedContext();
    }
    
    protected PanelPageItem instance(String name) {
        PanelPageItem item = new PanelPageItem();
        
        item.name = name;
        items.put(name, item);
        return item;
    }
    
    public final void setContext(PageBuilderContext context) {
        this.context = context;
    }
}