package org.iocaste.appbuilder.common.dashboard;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;

public class DashboardFactory {
    private Container container;
    private PageBuilderContext context;
    private Map<String, DashboardComponent> components;
    private StyleSheet stylesheet;
    private String stylename;
    
    public DashboardFactory(
            Container container, PageBuilderContext context, String name) {
        this.container = new StandardContainer(container, name);
        this.context = context;
        components = new LinkedHashMap<>();

        stylename = name.concat("_db_container");
        this.container.setStyleClass(stylename);
        
        stylename = String.format(".%s", stylename);
        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(stylename);
        stylesheet.put(stylename, "float", "left");
        stylesheet.put(stylename, "background-color", "#ffffff");
        
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
        stylesheet.put(".db_dash_item:hover", "text-decoration", "underline");
        stylesheet.put(".db_dash_item:hover", "padding", "3px");
        stylesheet.put(".db_dash_item:hover", "font-size", "12pt");
        stylesheet.put(".db_dash_item:hover", "color", "#0000ff");
        
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
    
    public final Set<String> getItems() {
        return components.keySet();
    }
    
    public final DashboardComponent instance(String name) {
        return instance(name, container, null);
    }
    
    public final DashboardComponent instance(String name, Container container,
            String group) {
        DashboardComponent component = new DashboardComponent(
                this, container, context, name, group);
        components.put(name, component);
        return component;
    }
    
    public final void isometricGrid() {
        isometricGrid(components.keySet());
    }
    
    public final void isometricGrid(Set<String> dashes) {
        double width;
        int qt, c;
        boolean compensate;
        
        width = Math.sqrt(dashes.size());
        compensate = ((width % 2) == 0);
        c = qt = (int)((!compensate)? width + 1 : width);
        width = 100 / qt;
        
        for (String name : dashes)
            if (compensate && c == qt) {
                components.get(name).setArea((int)width - 1, (int)width, "%");
                c = 1;
            } else {
                components.get(name).setArea((int)width, (int)width, "%");
                c++;
            }
    }
    
    public final void setArea(int width, int height, String unit) {
        setArea(stylename, stylesheet, width, height, unit);
    }
    
    public final void setArea(String stylename, StyleSheet stylesheet,
            int width, int height, String unit) {
        String swidth = String.format("%d%s", width, unit);
        String sheight = String.format("%d%s", height, unit);
        
        stylesheet.put(stylename, "height", sheight);
        stylesheet.put(stylename, "width", swidth);
    }
    
    public final void setGrid(int columns, int lines) {
        for (String name : components.keySet())
            components.get(name).setArea(100/columns, 100/lines, "%");
    }
    
    public final void setStyleProperty(String name, String value) {
        stylesheet.put(stylename, name, value);
    }
}
