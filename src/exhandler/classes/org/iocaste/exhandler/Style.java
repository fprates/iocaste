package org.iocaste.exhandler;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class Style extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        instance(".std_panel_content");
        put("width", "100%");
        put("height", "100%");
        put("font-size", "12pt");
        put("position", "relative");
        put("background-color", "#ffffff");
        put("overflow", "auto");
        put("padding-left", "0.5em");
    }
}