package org.iocaste.external;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class Style extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        instance(".xtrnl_import");
        put("margin", "0px");
        put("padding", "0px");
    }
    
}
