package org.iocaste.appbuilder.common.dashboard;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.iocaste.documents.common.DataType;
import org.iocaste.shell.common.Container;

public class DashboardComponent {
    public static final boolean GROUP = true;
    private String name, group;
    private DashboardFactory factory;
    private boolean hide;
    private Set<String> components;
    private List<ContentEntry> content;
    private DashboardRenderer renderer;
    
    public DashboardComponent(
            DashboardFactory factory, String name) {
        this(factory, name, null);
    }

    public DashboardComponent(
            DashboardFactory factory, String name, String group) {
        this.name = name;
        this.group = group;
        this.factory = factory;
        
        content = new ArrayList<>();
        components = new LinkedHashSet<>();
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
    
    public final void build() {
        Container container;

        renderer = factory.getRenderer();
        container = renderer.getContainer(name, DashboardRenderer.OUTER);
        if (!hide)
            container.setVisible(!hide);
        else
            if (container.getElements().size() > 0)
                container.setVisible(true);
        
        for (ContentEntry entry : content) {
            renderer.add(name, entry.name, entry.value, entry.dtype);
            renderer.setVisible(name, entry.visible);
        }
    }
    
    public final Set<String> getComponents() {
        return components;
    }
    
    public final DashboardFactory getFactory() {
        return factory;
    }
    
    public final String getGroup() {
        return group;
    }
    
    public final int geti() {
        return renderer.getInput(name).geti();
    }
    
    public final long getl() {
        return renderer.getInput(name).getl();
    }
    
    public final String getst() {
        return renderer.getInput(name).getst();
    }
    
    public final void hide() {
        hide = true;
    }
    
    public final void instance(String name) {
        factory.instance(name, this.name);
        components.add(name);
    }
    
    private final void internalAdd(String name, Object value, int type) {
        if (name == null)
            throw new RuntimeException("dashboard item undefined.");
        
        content.add(new ContentEntry(name, value, type));
    }
    
    public final void show() {
        hide = false;
    }
}

class ContentEntry {
    public String name;
    public Object value;
    public int dtype;
    public boolean visible;
    
    public ContentEntry(String name) {
        this.name = name;
    }
    
    public ContentEntry(String name, Object value, int dtype) {
        this.name = name;
        this.value = value;
        this.dtype = dtype;
    }
}