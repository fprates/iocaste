package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.factories.ButtonFactory;
import org.iocaste.appbuilder.common.factories.DataFormFactory;
import org.iocaste.appbuilder.common.factories.ExpandBarFactory;
import org.iocaste.appbuilder.common.factories.FileUploadFactory;
import org.iocaste.appbuilder.common.factories.FormFactory;
import org.iocaste.appbuilder.common.factories.FrameFactory;
import org.iocaste.appbuilder.common.factories.LinkFactory;
import org.iocaste.appbuilder.common.factories.ListBoxFactory;
import org.iocaste.appbuilder.common.factories.NavControlFactory;
import org.iocaste.appbuilder.common.factories.NodeListFactory;
import org.iocaste.appbuilder.common.factories.NodeListItemFactory;
import org.iocaste.appbuilder.common.factories.ParameterFactory;
import org.iocaste.appbuilder.common.factories.PrintAreaFactory;
import org.iocaste.appbuilder.common.factories.RadioButtonFactory;
import org.iocaste.appbuilder.common.factories.RadioGroupFactory;
import org.iocaste.appbuilder.common.factories.ReportToolFactory;
import org.iocaste.appbuilder.common.factories.SpecFactory;
import org.iocaste.appbuilder.common.factories.StandardContainerFactory;
import org.iocaste.appbuilder.common.factories.TabbedPaneFactory;
import org.iocaste.appbuilder.common.factories.TabbedPaneItemFactory;
import org.iocaste.appbuilder.common.factories.TableToolFactory;
import org.iocaste.appbuilder.common.factories.TextEditorFactory;
import org.iocaste.appbuilder.common.factories.TextFactory;
import org.iocaste.appbuilder.common.factories.TextFieldFactory;
import org.iocaste.appbuilder.common.factories.TilesFactory;
import org.iocaste.appbuilder.common.factories.VirtualControlFactory;

public class ViewSpecItem {
    
    public enum TYPES {
        BUTTON("button", new ButtonFactory()),
        DATA_FORM("dataform", new DataFormFactory()),
        EXPAND_BAR("expandbar", new ExpandBarFactory()),
        FORM("form", new FormFactory()),
        FILE_UPLOAD("fileupload", new FileUploadFactory()),
        FRAME("frame", new FrameFactory()),
        LINK("link", new LinkFactory()),
        LISTBOX("listbox", new ListBoxFactory()),
        NODE_LIST("nodelist", new NodeListFactory()),
        NODE_LIST_ITEM("nodelistitem", new NodeListItemFactory()),
        PAGE_CONTROL("navcontrol", new NavControlFactory()),
        PRINT_AREA("printarea", new PrintAreaFactory()),
        RADIO_BUTTON("radiobutton", new RadioButtonFactory()),
        RADIO_GROUP("radiogroup", new RadioGroupFactory()),
        REPORT_TOOL("reporttool", new ReportToolFactory()),
        STANDARD_CONTAINER("standardcontainer", new StandardContainerFactory()),
        TABBED_PANE("tabbedpane", new TabbedPaneFactory()),
        TABBED_PANE_ITEM("tabbedpaneitem", new TabbedPaneItemFactory()),
        TABLE_TOOL("tabletool", new TableToolFactory()),
        TEXT("text", new TextFactory()),
        TEXT_EDITOR("texteditor", new TextEditorFactory()),
        TEXT_FIELD("textfield", new TextFieldFactory()),
        TILES("tiles", new TilesFactory()),
        PARAMETER("parameter", new ParameterFactory()),
        VIEW("view", null),
        VIRTUAL_CONTROL("virtualcontrol", new VirtualControlFactory());

        private String name;
        private SpecFactory factory;
        
        TYPES(String name, SpecFactory factory) {
            this.name = name;
            this.factory = factory;
        }
        
        public SpecFactory factory() {
            return factory;
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
