package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class ProjectViewerStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        instance(".wb_tt_viewer");
        put("display", "block");
        put("vertical-align", "top");
        
        instance(".wb_dt_viewer");
        put("display", "block");
    }
    
}