package org.iocaste.appbuilder.common.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;

public class DashboardFactory {
    private Container container;
    private PageBuilderContext context;
    private Map<String, DashboardComponent> components;
    
    public DashboardFactory(
            Container container, PageBuilderContext context, String name) {
        StyleSheet stylesheet = context.view.styleSheetInstance();
        this.container = new StandardContainer(container, name);
        this.context = context;
        components = new HashMap<>();
        
        stylesheet.newElement(".db_container");
        stylesheet.put(".db_container", "float", "left");
        this.container.setStyleClass("db_container");
        
        stylesheet.newElement(".db_dash_item");
        stylesheet.put(".db_dash_item", "display", "block");
        stylesheet.put(".db_dash_item", "text-decoration", "none");
        stylesheet.put(".db_dash_item", "padding", "3px");
        stylesheet.put(".db_dash_item", "font-size", "12pt");
    }
    
    public final DashboardComponent get(String name) {
        return components.get(name);
    }
    
    public final DashboardComponent instance(String name) {
        DashboardComponent component = new DashboardComponent(
                container, context, name);
        
        components.put(name, component);
        return component;
    }
}
