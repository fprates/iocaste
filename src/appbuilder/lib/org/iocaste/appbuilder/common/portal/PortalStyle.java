package org.iocaste.appbuilder.common.portal;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;
import org.iocaste.shell.common.Shell;

public class PortalStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        String CLICKABLE_COLOR, FONT_FAMILY, FONT_COLOR;
        String FONT_SIZE;
        
        CLICKABLE_COLOR = constant(Shell.CLICKABLE_COLOR);
        FONT_FAMILY = constant(Shell.FONT_FAMILY);
        FONT_COLOR = constant(Shell.FONT_COLOR);
        FONT_SIZE = constant(Shell.FONT_SIZE);
        
        instance(".portal_viewport");
        put("list-style-type", "none");
        put("margin-left", "auto");
        put("margin-right", "auto");
        put("margin-top", "0px");
        put("margin-bottom", "0px");
        put("padding", "5px");
        
        instance(".portal_tile_text");
        put("margin", "0px");
        put("padding", "0px");
        put("font-family", FONT_FAMILY);
        put("font-size", FONT_SIZE);
        put("color", FONT_COLOR);
        put("font-style", "normal");
        put("display", "inline");
        put("display", "inline-block");
        put("white-space", "nowrap");
        put("text-overflow", "ellipsis");
        put("overflow", "hidden");
        
        clone(".portal_tile_key", ".portal_tile_text");
        put("color", CLICKABLE_COLOR);
        put("display", "block");
        put("font-size", "16pt");
        put("padding-top", "5px");
        put("padding-left", "5px");
        put("padding-right", "5px");
        put("padding-bottom", "5px");
        put("margin-bottom", "5px");
        put("border-bottom-style", "solid");
        put("border-bottom-width", "1px");
        put("border-bottom-color", "transparent");
        put("border-image", "linear-gradient(to right, #298eea, #ffffff) 1");
        
        forEachMedia(new PortalStyleMedia());
    }
	
}

class PortalStyleMedia extends AbstractViewConfigStyle {
    
    public void execute() {
        String FRAME_COLOR = constant(Shell.FRAME_COLOR);
        String BACKGROUND_COLOR = constant(Shell.BACKGROUND_COLOR);
        boolean ismobile = getMediaKey().startsWith("mobile");
        
        instance(".portal_tile_frame");
        put("width", ismobile? "calc(100% - 35px)" : "calc((100% / 3) - 35px)");
        put("border-bottom-style", "solid");
        put("border-bottom-width", "1px");
        put("border-bottom-color", FRAME_COLOR);
        put("border-radius", "5px");
        put("margin", "5px");
        put("padding", "10px");
        put("display", "inline-block");
        put("background-color", BACKGROUND_COLOR);
        put("box-shadow", "1px 1px 2px #505050");
        put("transition-property", "background");
        put("transition-duration", "0.5s");
        put("list-style", "none");
        
        clone(".portal_tile_frame:hover", ".portal_tile_frame");
        put("background-color", FRAME_COLOR);
    }
}