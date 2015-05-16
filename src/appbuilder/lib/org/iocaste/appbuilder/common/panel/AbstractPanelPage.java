package org.iocaste.appbuilder.common.panel;

import java.util.HashMap;
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
    private Map<Colors, String> colors;
    public Map<String, PanelPageItem> items;
    
    public AbstractPanelPage() {
        items = new LinkedHashMap<>();

        colors = new HashMap<>();
        colors.put(Colors.BODY_BG, "#202020");
        colors.put(Colors.COMPONENT_BG, "#303030");
        colors.put(Colors.HEAD_BG, "#303030");
        colors.put(Colors.DASH_BG, "#5050ff");
        colors.put(Colors.FOCUS, "#505050");
        colors.put(Colors.FONT, "#ffffff");
        colors.put(Colors.GROUP_BG, "#606060");
    }
    
    public abstract void execute();
    
    public final Map<Colors, String> getColors() {
        return colors;
    }
    
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
    
    protected final void set(Colors type, String value) {
        colors.put(type, value);
    }
    
    public final void setViewContext(ViewContext view) {
        this.view = view;
    }
}