package org.iocaste.runtime.common.page;

public class ViewSpecItem {

	public enum TYPES {
        BUTTON("button"),
        DATA_FORM("dataform", true),
        EXPAND_BAR("expandbar"),
        FORM("form"),
        FILE_UPLOAD("fileupload"),
        FRAME("frame"),
        LINK("link"),
        LISTBOX("listbox"),
        NODE_LIST("nodelist"),
        NODE_LIST_ITEM("nodelistitem"),
        PAGE_CONTROL("navcontrol"),
        PRINT_AREA("printarea"),
        RADIO_BUTTON("radiobutton"),
        RADIO_GROUP("radiogroup"),
        REPORT_TOOL("reporttool", true),
        STANDARD_CONTAINER("standardcontainer"),
        TABBED_PANE("tabbedpane"),
        TABBED_PANE_ITEM("tabbedpaneitem"),
        TABLE_TOOL("tabletool", true),
        TEXT("text"),
        TEXT_EDITOR("texteditor"),
        TEXT_FIELD("textfield"),
        TILES("tiles", true),
        PARAMETER("parameter"),
        VIEW("view"),
        VIRTUAL_CONTROL("virtualcontrol");

        private String name;
        private boolean composed;
        
        TYPES(String name) {
            this.name = name;
        }
        
        TYPES(String name, boolean composed) {
        	this(name);
        	this.composed = composed;
        }
        
        public final boolean isComposed() {
        	return composed;
        }
        
        @Override
        public final String toString() {
            return name;
        }
    };
    
    private String parent, name;
    private TYPES type;
    
    public ViewSpecItem(String parent, TYPES type, String name) {
        this.parent = parent;
        this.type = type;
        this.name = name;
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getParent() {
        return parent;
    }
    
    public final TYPES getType() {
        return type;
    }
}
