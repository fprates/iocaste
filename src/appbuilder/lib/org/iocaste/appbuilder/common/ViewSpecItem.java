package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.List;

public class ViewSpecItem {
    
    public enum TYPES {        
        VIEW("view"),
        FORM("form"),
        PAGE_CONTROL("navcontrol"),
        TABBED_PANE("tabbedpane"),
        TABBED_PANE_ITEM("tabbedpaneitem"),
        STANDARD_CONTAINER("standardcontainer"),
        TEXT_EDITOR("texteditor"),
        TABLE_TOOL("tabletool"),
        DATA_FORM("dataform"),
        DASHBOARD("dashboard"),
        DASHBOARD_ITEM("dashboarditem"),
        EXPAND_BAR("expandbar"),
        NODE_LIST("nodelist"),
        TEXT("text"),
        LINK("link"),
        REPORT_TOOL("reporttool"),
        FILE_UPLOAD("fileupload"),
        BUTTON("button"),
        RADIO_BUTTON("radiobutton"),
        RADIO_GROUP("radiogroup");

        private String name;
        
        TYPES(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    };
    
    private String parent, name;
    private int type;
    private List<ViewSpecItem> items;
    
    public ViewSpecItem(String parent, TYPES type, String name) {
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
}
