package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;

public class ProjectsTileConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Link link;
        
        link = getElement("item");
        link.setAction("project");
        getElement("frame").setStyleClass("wb_project_frame");
        getElement("name").setStyleClass("wb_project_name");
        getElement("title").setStyleClass("wb_project_title");
    }
    
}

