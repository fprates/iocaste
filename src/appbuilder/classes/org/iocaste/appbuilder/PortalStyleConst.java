package org.iocaste.appbuilder;

import org.iocaste.shell.common.Shell;

public class PortalStyleConst {
    
    public static final Object[][] PORTAL_STYLE_DATA = {
        {new String[] {".portal_viewport", null}, new Object[][] {
            {"list-style-type", "none", null},
            {"margin-left", "auto", null},
            {"margin-right", "auto", null},
            {"margin-top", "0px", null},
            {"margin-bottom", "0px", null},
            {"padding", "5px", null},
            {"width", "calc(100% - 35px)"} }
        },
        {new String[] {".portal_mini_viewport", ".portal_viewport"},
            new Object[][] {
                {"width", "calc((100% / 3) - 35px)"} }
        },
        {new String[] {".portal_viewport_node", null}, new Object[][] { } },
        {new String[] {".portal_tile_text", null}, new Object[][] {
            {"margin", "0px", null},
            {"padding", "0px", null},
            {"font-family", null, Shell.FONT_FAMILY},
            {"font-size", null, Shell.FONT_SIZE},
            {"color", null, Shell.FONT_COLOR},
            {"font-style", "normal", null},
            {"display", "inline", null},
            {"display", "inline-block", null},
            {"white-space", "nowrap", null},
            {"text-overflow", "ellipsis", null},
            {"overflow", "hidden", null} }
        },
        {new String[] {".portal_tile_key", ".portal_tile_text"},
            new Object[][] {
                {"color", null, Shell.CLICKABLE_COLOR},
                {"display", "block", null},
                {"font-size", "16pt", null},
                {"padding-top", "5px", null},
                {"padding-left", "5px", null},
                {"padding-right", "5px", null},
                {"padding-bottom", "5px", null},
                {"margin-bottom", "5px", null},
                {"border-bottom-style", "solid", null},
                {"border-bottom-width", "1px", null},
                {"border-bottom-color", "transparent", null},
                {"border-image",
                    "linear-gradient(to right, #298eea, #ffffff) 1", null} }
        },
        {new String[] {".portal_tile_frame", null}, new Object[][] {
            {"border-bottom-style", "solid", null},
            {"border-bottom-width", "1px", null},
            {"border-bottom-color", null, Shell.FRAME_COLOR},
            {"border-radius", "5px", null},
            {"margin", "5px", null},
            {"padding", "10px", null},
            {"display", "inline-block", null},
            {"background-color", null, Shell.BACKGROUND_COLOR},
            {"box-shadow", "1px 1px 2px #505050", null},
            {"transition-property", "background", null},
            {"transition-duration", "0.5s", null},
            {"list-style", "none", null} }
        },
        {new String[] {".portal_tile_frame:hover", ".portal_tile_frame"},
            new Object[][] {
                {"background-color", null, Shell.FRAME_COLOR} }
        }
    };

}
