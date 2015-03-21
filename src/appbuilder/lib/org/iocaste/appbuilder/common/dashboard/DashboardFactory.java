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
        String group;
        DashboardComponent component;
        Container container, factorygroup;
        
        renderer.config();
        factorygroup = context.view.getElement(name);
        for (String name : components.keySet()) {
            component = components.get(name);
            group = component.getGroup();
            if (group == null)
                container = factorygroup;
            else
                container = renderer.getContainer(
                        group, DashboardRenderer.INNER);
            
            renderer.entryInstance(group, name);
            renderer.config(name);
            renderer.build(container, name);
            component.build();
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
        return instance(name, null);
    }
    
    public final DashboardComponent instance(String name, String group) {
        DashboardComponent component;
        
        component = new DashboardComponent(this, name, group);
        components.put(name, component);
        return component;
    }
    
    public final void setRenderer(DashboardRenderer renderer) {
        this.renderer = renderer;
        this.renderer.setContext(context);
        this.renderer.entryInstance(null, name);
        this.renderer.setStyle(context.view.getElement(name).getStyleClass());
    }
    
    public final void setStyleProperty(String name, String value) {
        renderer.setStyleProperty(name, value);
    }
}
