package org.iocaste.appbuilder.common.portal;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;
import org.iocaste.shell.common.Shell;

public class PortalStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        load(".nc_login");
        put("display", "none");
        
        instance(".portal_viewport");
        put("max-width", "400px");
        put("list-style-type", "none");
        put("margin-left", "auto");
        put("margin-right", "auto");
        put("margin-top", "0px");
        put("margin-bottom", "0px");
        put("padding", "5px");
        
        forEachMedia(new PortalStyleMedia());
    }
	
}

class PortalStyleMedia extends AbstractViewConfigStyle {
    
    public void execute() {
        String width, FRAME_COLOR, BACKGROUND_COLOR;
        
        BACKGROUND_COLOR = constant(Shell.BACKGROUND_COLOR);
        FRAME_COLOR = constant(Shell.FRAME_COLOR);
        
        load(".content_area");
        width = get("max-width");
        
        instance(".tile_frame");
        put("border-bottom-style", "solid");
        put("border-bottom-width", "1px");
        put("border-bottom-color", FRAME_COLOR);
        put("margin", "0.5em");
        put("display", "block");
        put("width", "calc(100% - 1em)");
        put("float", "left");
        put("background-color", BACKGROUND_COLOR);
        put("box-shadow", constant(Shell.SHADOW));
        put("padding", "0px");
        put("list-style-type", "none");
        if (!getMediaKey().startsWith("mobile"))
            put("max-width", "calc((%s / 4) - 2em)", width);
        clone(".tile_frame:hover", ".tile_frame");
        put("background-color", FRAME_COLOR);
    }
}