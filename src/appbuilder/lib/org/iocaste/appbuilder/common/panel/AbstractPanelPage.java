package org.iocaste.appbuilder.common.panel;

import java.util.HashMap;
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
    private Map<Colors, String> colors;
    private Set<String> actions;
    private String submit;
    public Map<String, PanelPageItem> items;
    
    public AbstractPanelPage() {
        items = new LinkedHashMap<>();
        actions = new HashSet<>();
        
        colors = new HashMap<>();
        colors.put(Colors.CONTENT_BG, "#202020");
        colors.put(Colors.COMPONENT_BG, "#303030");
        colors.put(Colors.HEAD_BG, "#303030");
        colors.put(Colors.DASH_BG, "#f0f0f0");
        colors.put(Colors.DASH_BORDER, "#e0e0e0");
        colors.put(Colors.DASH_FONT, "#000000");
        colors.put(Colors.FOCUS, "#505050");
        colors.put(Colors.FOCUS_FONT, "#ffffff");
        colors.put(Colors.FONT, "#ffffff");
        colors.put(Colors.GROUP_BG, "#606060");
        colors.put(Colors.ACTION_BG, "#3030ff");
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
    
    public final Map<Colors, String> getColors() {
        return colors;
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
    
    protected final void set(Colors type, String value) {
        colors.put(type, value);
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