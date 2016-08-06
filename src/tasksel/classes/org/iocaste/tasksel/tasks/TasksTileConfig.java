package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;

public class TasksTileConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Link tileitem;
        
        getElement("frame").setStyleClass("tile_frame");
        getElement("text").setStyleClass("tile_text");
        
        tileitem = getElement("tileitem");
        tileitem.setAction("task");
        tileitem.setStyleClass("nc_tiles_item");
    }
    
}