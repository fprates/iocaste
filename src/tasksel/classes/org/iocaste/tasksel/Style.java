package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;
import org.iocaste.shell.common.Shell;

public class Style extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        instance(".tile_frame");
        put("border-bottom-style", "solid");
        put("border-bottom-width", "1px");
        put("border-bottom-color", constant(Shell.FRAME_COLOR));
        put("cursor", "pointer");
        
        clone(".tile_frame:hover", ".tile_frame");
        put("background-color", constant(Shell.FRAME_COLOR));
        
        instance(".tile_text");
        put("margin", "0px");
        put("font-family", constant(Shell.FONT_FAMILY));
        put("font-size", "12pt");
        put("color", constant(Shell.FONT_COLOR));
        put("font-style", "normal");
        put("padding", "1em");
        
        instance(".tilestyle");
        put("border-style", "solid");
    }
}
