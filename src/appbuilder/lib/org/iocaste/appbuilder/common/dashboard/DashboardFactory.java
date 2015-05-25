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
    private String container;
    
    public DashboardFactory(
            Container parent, PageBuilderContext context, String name) {
        Container container;
        String style;
        
        this.context = context;
        this.name = name;
        components = new LinkedHashMap<>();

        style = name.concat("_db_container");
        container = new StandardContainer(parent, name);
        container.setStyleClass(style);
        this.container = container.getHtmlName();
        
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
        String style;
        
        style = new StringBuilder(".").append(
                context.view.getElement(name).getStyleClass()).toString();
        
        this.renderer = renderer;
        this.renderer.setContainerName(container);
        this.renderer.setContext(context);
        this.renderer.entryInstance(null, name);
        this.renderer.setStyle(style);
    }
    
    public final void setStyleProperty(String name, String value) {
        renderer.setStyleProperty(name, value);
    }
}
