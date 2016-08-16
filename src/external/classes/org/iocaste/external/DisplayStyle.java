package org.iocaste.external;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class DisplayStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        instance(".xtrnl_import");
        put("margin", "0px");
        put("padding", "0px");
        put("display", "none");
        put("height", "0px");
        put("width", "0px");
        
        instance(".xtrnl_import_item");
        put("display", "inline");
        put("float", "left");
        put("margin", "0px");
        put("padding", "0px");
        put("display", "none");
        put("height", "0px");
        put("width", "0px");
        
        clone(".xtrnl_import_form", ".form");
        put("margin", "0px");
        put("padding", "0px");
        put("display", "none");
        put("height", "0px");
        put("width", "0px");
    }

}
