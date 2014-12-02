package org.iocaste.appbuilder.common.dashboard;

import java.util.LinkedHashSet;
import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataType;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;

public class DashboardComponent {
    public static final boolean GROUP = true;
    private String choose, name, stylename, innerstyle, unit;
    private PageBuilderContext context;
    private StandardContainer container, inner;
    private StyleSheet stylesheet;
    private DashboardFactory factory;
    private boolean hide;
    private int width, height;
    private Set<String> components;
    
    public DashboardComponent(DashboardFactory factory, Container container,
            PageBuilderContext context, String name) {
        init(factory, container, context, name, null);
    }
    

    public DashboardComponent(DashboardFactory factory, Container container,
            PageBuilderContext context, String name, String group) {
        init(factory, container, context, name, group);
    }
    
    public final void add(String item) {
        internalAdd(item, item, DataType.CHAR);
    }
    
    public final void add(String item, String value) {
        internalAdd(item, value, DataType.CHAR);
    }
    
    public final void add(String item, int value) {
        internalAdd(item, value, DataType.INT);
    }
    
    public final void add(String item, long value) {
        internalAdd(item, value, DataType.LONG);
    }
    
    public final void add(String item, byte value) {
        internalAdd(item, value, DataType.BYTE);
    }
    
    public final void add(String item, short value) {
        internalAdd(item, value, DataType.SHORT);
    }
    
    public final void addText(String name) {
        String textname;
        Text text;
        
        if (!hide)
            container.setVisible(true);
        
        textname = name.concat(".item");
        text = new Text(inner, textname);
        text.setText(name);
    }
    
    private final void commit() {
        factory.setArea(stylename, stylesheet, width, height, unit);
    }
    
    public final Set<String> getComponents() {
        return components;
    }
    
    public final DashboardFactory getFactory() {
        return factory;
    }
    
    private final InputComponent getInput() {
        return context.view.getElement(choose);
    }
    
    public final int geti() {
        return getInput().geti();
    }
    
    public final long getl() {
        return getInput().getl();
    }
    
    public final String getst() {
        return getInput().getst();
    }
    
    public final void hide() {
        hide = true;
        container.setVisible(false);
    }
    
    private final void init(DashboardFactory factory, Container container,
                PageBuilderContext context, String name, String group) {
        this.context = context;
        this.name = (group == null)? name : group;
        this.factory = factory;

        components = new LinkedHashSet<>();
        choose = this.name.concat("_dbitem_choose");
        stylename = ".db_dash_".concat(name);

        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(stylename);
        stylesheet.put(stylename, "border-style", "solid");
        stylesheet.put(stylename, "border-width", "1px");
        stylesheet.put(stylename, "margin", "1px");
        
        this.container = new StandardContainer(
                container, name.concat("_container"));
        this.container.setStyleClass(stylename.substring(1));
        this.container.setVisible(false);
        
        innerstyle = ".db_dash_inner_".concat(name);
        stylesheet.newElement(innerstyle);
        stylesheet.put(innerstyle, "margin", "10px");
        
        this.inner = new StandardContainer(this.container,
                name.concat("_inner"));
        this.inner.setStyleClass(innerstyle.substring(1));
    }
    
    public final void instance(String name) {
        factory.instance(name, inner, this.name);
        components.add(name);
        if (!hide)
            container.setVisible(true);
    }
    
    private final void internalAdd(String name, Object value, int type) {
        String linkname;
        Link link;
        
        if (!hide)
            container.setVisible(true);
        
        if (name == null)
            throw new RuntimeException("dashboard item undefined.");
        
        linkname = name.concat("_dbitem_link");
        link = new Link(inner, linkname, this.name);
        link.setStyleClass("db_dash_item");
        link.setText(name);
        link.add(choose, value, type);
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
    
    public final void setInnerStyle(String name, String value) {
        stylesheet.put(innerstyle, name, value);
    }
    
    public final void setStyleProperty(String name, String value) {
        stylesheet.put(stylename, name, value);
    }
    
    public final void show() {
        hide = false;
        if (container.getElements().size() > 0)
            container.setVisible(true);
    }
}
