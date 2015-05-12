package org.iocaste.appbuilder.common.panel;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.ViewContext;

public abstract class AbstractPanelPage {
    private ViewContext view;
    private AbstractPanelSpec spec;
    private AbstractViewConfig config;
    private AbstractViewInput input;
    public Map<String, PanelPageItem> items;
    
    
    public AbstractPanelPage() {
        items = new LinkedHashMap<>();
    }
    public abstract void execute();
    
    public final AbstractViewConfig getConfig() {
        return config;
    }
    
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return view.getExtendedContext();
    }
    
    public final AbstractViewInput getInput() {
        return input;
    }
    
    public final AbstractPanelSpec getSpec() {
        return spec;
    }
    
    protected PanelPageItem instance(String name) {
        PanelPageItem item = new PanelPageItem();
        
        item.name = name;
        items.put(name, item);
        return item;
    }
    
    protected final void put(String action, AbstractActionHandler handler) {
        view.put(action, handler);
    }
    
    protected final void set(AbstractViewConfig config) {
        this.config = config;
    }
    
    protected final void set(AbstractViewInput input) {
        this.input = input;
    }
    
    protected final void set(AbstractPanelSpec spec) {
        this.spec = spec;
    }
    
    public final void setViewContext(ViewContext view) {
        this.view = view;
    }
}