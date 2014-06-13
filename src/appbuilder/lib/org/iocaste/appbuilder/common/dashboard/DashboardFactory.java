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
        stylesheet.put(".db_container", "width", "200px");
        this.container.setStyleClass("db_container");
        
        stylesheet.newElement(".db_dash_item:link");
        stylesheet.put(".db_dash_item:link", "display", "block");
        stylesheet.put(".db_dash_item:link", "text-decoration", "none");
        stylesheet.put(".db_dash_item:link", "padding", "3px");
        stylesheet.put(".db_dash_item:link", "font-size", "12pt");
        stylesheet.put(".db_dash_item:link", "color", "#0000ff");
        
        stylesheet.newElement(".db_dash_item:active");
        stylesheet.put(".db_dash_item:active", "display", "block");
        stylesheet.put(".db_dash_item:active", "text-decoration", "none");
        stylesheet.put(".db_dash_item:active", "padding", "3px");
        stylesheet.put(".db_dash_item:active", "font-size", "12pt");
        stylesheet.put(".db_dash_item:active", "color", "#0000ff");
        
        stylesheet.newElement(".db_dash_item:hover");
        stylesheet.put(".db_dash_item:hover", "display", "block");
        stylesheet.put(".db_dash_item:hover", "text-decoration", "none");
        stylesheet.put(".db_dash_item:hover", "padding", "3px");
        stylesheet.put(".db_dash_item:hover", "font-size", "12pt");
        stylesheet.put(".db_dash_item:hover", "color", "#ffffff");
        
        stylesheet.newElement(".db_dash_item:visited");
        stylesheet.put(".db_dash_item:visited", "display", "block");
        stylesheet.put(".db_dash_item:visited", "text-decoration", "none");
        stylesheet.put(".db_dash_item:visited", "padding", "3px");
        stylesheet.put(".db_dash_item:visited", "font-size", "12pt");
        stylesheet.put(".db_dash_item:visited", "color", "#0000ff");
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
