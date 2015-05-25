package org.iocaste.appbuilder.common.panel;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.panel.context.PanelPageItemContext;

public abstract class AbstractPanelPage {
    private ViewContext view;
    private AbstractPanelSpec spec;
    private ViewConfig config;
    private AbstractViewInput input;
    private Set<String> actions;
    private String submit;
    public Map<String, PanelPageItem> items;
    
    public AbstractPanelPage() {
        items = new LinkedHashMap<>();
        actions = new HashSet<>();
    }
    
    protected void action(String action, AbstractActionHandler handler) {
        actions.add(action);
        put(action, handler);
    }
    
    protected PanelPageItemContext contextitem(String name) {
        PanelPageItem item;
        
        item = instance(name);
        item.dashboard = false;
        return item.context;
    }
    
    public abstract void execute();
    
    public final Set<String> getActions() {
        return actions;
    }
    
    public final ViewConfig getConfig() {
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
    
    public final String getSubmit() {
        return submit;
    }
    
    protected PanelPageItem instance(String name) {
        PanelPageItem item = new PanelPageItem();
        
        item.name = name;
        item.dashboard = true;
        items.put(name, item);
        return item;
    }
    
    protected final void put(String action, AbstractActionHandler handler) {
        view.put(action, handler);
    }
    
    protected final void set(ViewConfig config) {
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
    
    protected final void submit(String action, AbstractActionHandler handler) {
        submit = action;
        put(action, handler);
    }
    
    protected final void update() {
        view.setUpdate(true);
    }
}