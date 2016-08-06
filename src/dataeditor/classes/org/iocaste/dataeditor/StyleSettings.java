package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class StyleSettings extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        instance(".std_panel_content");
        put("overflow", "auto");
        put("font-size", "12pt");
        put("position", "relative");
        put("background-color", "#ffffff");
        put("width", "100%");
        put("height", "100%");
        put("padding-left", "0.5em");
    }

}
