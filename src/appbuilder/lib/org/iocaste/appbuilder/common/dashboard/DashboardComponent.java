package org.iocaste.appbuilder.common.dashboard;

import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewCustomAction;

public class DashboardComponent {
    private Container container;
    private AbstractContext context;
    private String name, actionname, stylename;
    private StandardContainer component;
    private StyleSheet stylesheet;
    
    public DashboardComponent(
            Container container, AbstractContext context, String name) {
        this.container = container;
        this.context = context;
        this.name = name;
        build();
    }
    
    public final void addText(String name) {
        String textname = name.concat(".item");
        Text text = new Text(component, textname);
        text.setText(name);
    }
    
    public final void add(String name) {
        String linkname = name.concat(".item");
        Link link = new Link(component, linkname, actionname);
        link.setStyleClass("db_dash_item");
        link.setText(name);
        link.add("db_action", name);
    }
    
    private final void build() {
        stylename = ".db_dash_"+name;
        component = new StandardContainer(container, name.concat("_container"));
        component.setStyleClass(stylename.substring(1));
        actionname = name.concat("_dashboard_item");

        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(stylename);
        stylesheet.put(stylename, "border-style", "none");
        stylesheet.put(stylename, "width", "150px");
        stylesheet.put(stylename, "padding", "20px");
        stylesheet.put(stylename, "margin", "2px");
    }
    
    public final void setAction(ViewCustomAction action) {
        AbstractPage page = (AbstractPage)context.function;
        page.register(actionname, action);
    }
    
    public final void setColor(String color) {
        stylesheet.put(stylename, "background-color", color);
    }
    
    public final void setVisible(boolean visible) {
        component.setVisible(visible);
    }
}
