package org.iocaste.packagetool.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewSpec implements Serializable {
    private static final long serialVersionUID = -7172737582110561651L;
    private Map<String, ViewSpecItem> items;
    private List<ViewSpecItem> sequence;
    private String view;
    
    public ViewSpec(String view) {
        items = new HashMap<>();
        sequence = new ArrayList<>();
        this.view = view;
    }
    
    public final void form(String name) {
        ViewSpecItem item = new ViewSpecItem(
                view, "view", ViewSpecItem.TYPES.FORM, name);
        items.put(name, item);
        sequence.add(item);
    }
    
    public final List<ViewSpecItem> getItems() {
        return sequence;
    }
    
    public final void navControl(String parent) {
        put(parent, ViewSpecItem.TYPES.PAGE_CONTROL, "navcontrol");
    }
    
    private final void put(String parent, ViewSpecItem.TYPES type, String name)
    {
        ViewSpecItem item = new ViewSpecItem(view, parent, type, name);
        
        items.get(parent).add(item);
        items.put(name, item);
    }
    
    public final void tabbedPane(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TABBED_PANE, name);
    }
    
    public final void tabbedPaneItem(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TABBED_PANE_ITEM, name);
    }
    
    public final void standardContainer(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.STANDARD_CONTAINER, name);
    }
    
    public final void textEditor(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TEXT_EDITOR, name);
    }
}
