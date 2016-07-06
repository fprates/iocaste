package org.iocaste.workbench;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;
import org.iocaste.shell.common.Shell;

public class MainStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        forEachMedia(new ProjectTileStyle());
        clone(".wb_project_name", ".text");
        put("text-align", "center");
        put("margin", "0px");
        
        clone(".wb_project_title", ".text");
        put("font-size", "9pt");
        put("text-align", "center");
        put("margin", "0px");
        
        instance(".wb_projects");
        put("margin", "0px");
        put("padding-top", "10px");
        put("padding-bottom", "10px");
        put("padding-left", "2px");
        put("padding-right", "2px");
        put("background-color", "#e0e0e0");
        
        instance("body");
        put("background-color", "#e0e0e0");
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
        put("border-style", "none");
        put("border-width", "0px");
        put("padding-top", "10px");
        put("padding-bottom", "10px");
        put("box-shadow", constant(Shell.SHADOW));
        put("background-color", constant(Shell.BACKGROUND_COLOR));
    }
    
}