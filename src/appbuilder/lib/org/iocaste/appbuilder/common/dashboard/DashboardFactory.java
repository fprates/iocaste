package org.iocaste.appbuilder.common.dashboard;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;

public class DashboardFactory {
    private String name;
    private PageBuilderContext context;
    private Map<String, DashboardComponent> components;
    private DashboardRenderer renderer;
    
    public DashboardFactory(
            Container container, PageBuilderContext context, String name) {
        String style;
        
        this.context = context;
        this.name = name;
        components = new LinkedHashMap<>();

        style = new StringBuilder(".").append(name).append("_db_container").
                toString();
        new StandardContainer(container, name).setStyleClass(style);
        setRenderer(new StandardDashboardRenderer());
    }
    
    public final void build() {
        Container container;
        
        container = context.view.getElement(name);
        renderer.config();
        for (String name : components.keySet()) {
            renderer.entryInstance(name);
            renderer.config(name);
            renderer.build(container, name);
            components.get(name).build();
        }
    }
    
    public final DashboardComponent get(String name) {
        return components.get(name);
    }
    
    public final Container getFactoryContainer() {
        return context.view.getElement(this.name);
    }
    
    public final Set<String> getItems() {
        return components.keySet();
    }
    
    public final DashboardRenderer getRenderer() {
        return renderer;
    }
    
    public final DashboardComponent instance(String name) {
        return instance(name, getFactoryContainer(), null);
    }
    
    public final DashboardComponent instance(String name, Container container,
            String group) {
        DashboardComponent component;
        
        component = new DashboardComponent(this, container, name, group);
        components.put(name, component);
        return component;
    }
    
    public final void setRenderer(DashboardRenderer renderer) {
        this.renderer = renderer;
        this.renderer.setContext(context);
        this.renderer.entryInstance(name);
        this.renderer.setStyle(context.view.getElement(name).getStyleClass());
    }
    
    public final void setStyleProperty(String name, String value) {
        renderer.setStyleProperty(name, value);
    }
}
