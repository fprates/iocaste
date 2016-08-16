package org.iocaste.external;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class EditStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        instance(".xtrnl_import");
        put("margin", "0px");
        put("padding", "0px");
        put("display", "block");
        put("height", "2em");
        
        instance(".xtrnl_import_item");
        put("display", "inline");
        put("float", "left");
        
        clone(".xtrnl_import_form", ".form");
        put("padding", "0px");
    }

}
