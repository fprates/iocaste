package org.iocaste.packagetool.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewSpecItem implements Serializable {
    private static final long serialVersionUID = -1030482879559951235L;

    public enum TYPES {
        FORM,
        PAGE_CONTROL,
        TABBED_PANE,
        TABBED_PANE_ITEM,
        STANDARD_CONTAINER,
        TEXT_EDITOR
    };
    
    private String parent, name, view;
    private int type;
    private List<ViewSpecItem> items;
    
    public ViewSpecItem(String view, String parent, TYPES type, String name) {
        this.view = view;
        this.parent = parent;
        this.type = type.ordinal();
        this.name = name;
        items = new ArrayList<>();
    }
    
    public final void add(ViewSpecItem item) {
        items.add(item);
    }
    
    public final List<ViewSpecItem> getItems() {
        return items;
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getParent() {
        return parent;
    }
    
    public final int getType() {
        return type;
    }
    
    public final String getView() {
        return view;
    }
}
