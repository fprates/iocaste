package org.iocaste.appbuilder.common.dashboard;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;

public class DashboardComponent {
    private String choose, name, stylename;
    private PageBuilderContext context;
    private StandardContainer component;
    private StyleSheet stylesheet;
    private boolean hide;
    
    public DashboardComponent(
            Container container, PageBuilderContext context, String name) {
        this.context = context;
        this.name = name;
        choose = name.concat("_dbitem_choose");
        stylename = ".db_dash_".concat(name);
        component = new StandardContainer(container, name.concat("_container"));
        component.setStyleClass(stylename.substring(1));

        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(stylename);
        stylesheet.put(stylename, "border-style", "none");
        stylesheet.put(stylename, "width", "150px");
        stylesheet.put(stylename, "padding", "20px");
        stylesheet.put(stylename, "margin", "2px");
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
        if (!hide)
            component.setVisible(true);
        
        String linkname = item.concat("_dbitem_link");
        Link link = new Link(component, linkname, name);
        link.setStyleClass("db_dash_item");
        link.setText(item);
        link.add(choose, item);
    }
    
    public final String getValue() {
        return ((InputComponent)context.view.getElement(choose)).getst();
    }
    
    public final void hide() {
        hide = true;
        component.setVisible(false);
    }
    
    public final void setColor(String color) {
        stylesheet.put(stylename, "background-color", color);
    }
    
    public final void show() {
        hide = false;
        if (component.getElements().length > 0)
            component.setVisible(true);
    }
}
