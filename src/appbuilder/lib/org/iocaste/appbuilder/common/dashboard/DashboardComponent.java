package org.iocaste.appbuilder.common.dashboard;

import java.util.HashSet;
import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;

public class DashboardComponent {
    public static final boolean GROUP = true;
    private String choose, name, stylename, unit, backcolor, bordercolor;
    private PageBuilderContext context;
    private StandardContainer container;
    private StyleSheet stylesheet;
    private DashboardFactory factory;
    private boolean hide, group;
    private int width, height, padding;
    private Set<String> components;
    
    public DashboardComponent(DashboardFactory factory, Container container,
            PageBuilderContext context, String name) {
        this(factory, container, context, name, false);
    }
    
    public DashboardComponent(DashboardFactory factory, Container container,
            PageBuilderContext context, String name, boolean group) {
        this.context = context;
        this.name = name;
        this.group = group;
        this.factory = factory;

        components = new HashSet<>();
        bordercolor = "black";
        backcolor = "white";
        choose = name.concat("_dbitem_choose");
        stylename = ".db_dash_".concat(name);
        this.container = new StandardContainer(container,
                name.concat("_container"));
        this.container.setStyleClass(stylename.substring(1));
        this.container.setVisible(false);

        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(stylename);
        stylesheet.put(stylename, "border-color", bordercolor);
        stylesheet.put(stylename, "background-color", backcolor);
        stylesheet.put(stylename, "border-style", "solid");
        
        if (this.group) {
            padding = 1;
            stylesheet.put(stylename, "float", "left");
            stylesheet.put(stylename, "padding-top", "1%");
            stylesheet.put(stylename, "padding-left", "1%");
            stylesheet.put(stylename, "padding-bottom", "0%");
            stylesheet.put(stylename, "padding-right", "0%");
        } else {
            stylesheet.put(stylename, "width", "150px");
            stylesheet.put(stylename, "margin", "2px");
            stylesheet.put(stylename, "padding", "20px");
        }
    }
    
    public final void add(String item) {
        add(item, item);
    }
    
    public final void add(String item, Object value) {
        String linkname;
        Link link;
        
        if (!hide)
            container.setVisible(true);
        
        if (item == null)
            throw new RuntimeException("dashboard item undefined.");
        
        linkname = item.concat("_dbitem_link");
        link = new Link(container, linkname, name);
        link.setStyleClass("db_dash_item");
        link.setText(item);
        link.add(choose, value);
    }
    
    public final void addText(String name) {
        String textname;
        Text text;
        
        if (!hide)
            container.setVisible(true);
        
        textname = name.concat(".item");
        text = new Text(container, textname);
        text.setText(name);
    }
    
    private final void commit() {
        factory.setArea(stylename, stylesheet,
                width - (padding * 2), height - (padding * 2), unit);
        stylesheet.put(stylename, "border-color", bordercolor);
        stylesheet.put(stylename, "background-color", backcolor);
        stylesheet.put(stylename, "padding-top",
                String.format("%d%s", padding, unit));
        stylesheet.put(stylename, "padding-left",
                String.format("%d%s", padding, unit));
    }
    
    public final DashboardFactory getFactory() {
        return factory;
    }
    
    public final String getValue() {
        return ((InputComponent)context.view.getElement(choose)).getst();
    }
    
    public final void hide() {
        hide = true;
        container.setVisible(false);
    }
    
    public final void instance(String name, boolean group) {
        factory.instance(name, container, group);
        components.add(name);
        if (!hide)
            container.setVisible(true);
    }
    
    public final void isometricGrid() {
        factory.isometricGrid(components);
    }
    
    public final void setArea(int width, int height, String unit) {
        this.width = width;
        this.height = height;
        this.unit = unit;
        commit();
    }
    
    public final void setBorderColor(String color) {
        bordercolor = color;
        commit();
    }
    
    public final void setColor(String color) {
        backcolor = color;
        for (String name : components)
            factory.get(name).setBorderColor(color);
        commit();
    }
    
    public final void setPadding(int padding) {
        this.padding = padding;
        commit();
    }
    
    public final void show() {
        hide = false;
        if (container.getElements().size() > 0)
            container.setVisible(true);
    }
}
