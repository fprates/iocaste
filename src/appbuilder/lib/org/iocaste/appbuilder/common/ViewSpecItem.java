package org.iocaste.appbuilder.common;

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
        EXPAND_BAR("expandbar"),
        NODE_LIST("nodelist"),
        TEXT("text"),
        LINK("link"),
        LISTBOX("listbox"),
        REPORT_TOOL("reporttool"),
        FILE_UPLOAD("fileupload"),
        BUTTON("button"),
        RADIO_BUTTON("radiobutton"),
        RADIO_GROUP("radiogroup"),
        TEXT_FIELD("textfield"),
        TILES("tiles"),
        PRINT_AREA("printarea"),
        PARAMETER("parameter");

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
    
    public ViewSpecItem(String parent, TYPES type, String name) {
        this.parent = parent;
        this.type = type.ordinal();
        this.name = name;
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
