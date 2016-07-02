package org.iocaste.workbench;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class MainStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        forEachMedia(new ProjectTileStyle());
        instance(".wb_project_name");
        put("text-align", "center");
    }

}

class ProjectTileStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        String maxwidth;
        
        load(".content_area");
        maxwidth = get("max-width");
        
        instance(".wb_project_frame");
        put("display", "inline-block");
        put("width", "calc(%s / 4 - 10px)", maxwidth);
    }
    
}