package org.iocaste.install;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class Style extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        load(".radio_button");
        put("display", "block");
        
        load(".text");
        put("padding-bottom", "1em");
        
        clone(".dbtypes", ".text");
        remove("padding-bottom");
    }
    
}