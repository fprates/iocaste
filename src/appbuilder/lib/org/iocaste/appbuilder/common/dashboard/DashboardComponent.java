package org.iocaste.appbuilder.common.dashboard;

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
    private StandardContainer component;
    private StyleSheet stylesheet;
    private boolean hide, group;
    private int width, height, padding;
    
    public DashboardComponent(Container container, PageBuilderContext context,
            String name) {
        this(container, context, name, false);
    }
    
    public DashboardComponent(Container container, PageBuilderContext context,
            String name, boolean group) {
        this.context = context;
        this.name = name;
        this.group = group;
        bordercolor = "black";
        backcolor = "white";
        
        choose = name.concat("_dbitem_choose");
        stylename = ".db_dash_".concat(name);
        component = new StandardContainer(container, name.concat("_container"));
        component.setStyleClass(stylename.substring(1));

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
        component.setVisible(false);
    }
    
    public final void addText(String name) {
        if (!hide)
            component.setVisible(true);
        
        String textname = name.concat(".item");
        Text text = new Text(component, textname);
        text.setText(name);
    }
    
    public final void add(String item) {
        add(item, item);
    }
    
    public final void add(String item, Object value) {
        if (!hide)
            component.setVisible(true);
        
        if (item == null)
            throw new RuntimeException("dashboard item undefined.");
        
        String linkname = item.concat("_dbitem_link");
        Link link = new Link(component, linkname, name);
        link.setStyleClass("db_dash_item");
        link.setText(item);
        link.add(choose, value);
    }
    
    private final void commit() {
        String swidth = String.format("%d%s", width - (padding * 2), unit);
        String sheight = String.format("%d%s", height - (padding * 2), unit);

        stylesheet.put(stylename, "border-color", bordercolor);
        stylesheet.put(stylename, "background-color", backcolor);
        stylesheet.put(stylename, "height", sheight);
        stylesheet.put(stylename, "width", swidth);
        stylesheet.put(stylename, "padding-top",
                String.format("%d%s", padding, unit));
        stylesheet.put(stylename, "padding-left",
                String.format("%d%s", padding, unit));
    }
    
    public final String getValue() {
        return ((InputComponent)context.view.getElement(choose)).getst();
    }
    
    public final void hide() {
        hide = true;
        component.setVisible(false);
    }
    
    public final void setArea(int width, int height, String unit) {
        this.width = width;
        this.height = height;
        this.unit = unit;
    }
    
    public final void setBorderColor(String color) {
        bordercolor = color;
        commit();
    }
    
    public final void setColor(String color) {
        backcolor = color;
        commit();
    }
    
    public final void setPadding(int padding) {
        this.padding = padding;
        commit();
    }
    
    public final void show() {
        hide = false;
        if (component.getElements().size() > 0)
            component.setVisible(true);
    }
}
