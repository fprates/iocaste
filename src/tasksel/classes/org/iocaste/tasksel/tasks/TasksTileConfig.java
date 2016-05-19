package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;
import org.iocaste.tasksel.Style;

public class TasksTileConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Link tileitem;
        
        Style.set(context);
        
        getElement("frame").setStyleClass("tile_frame");
        getElement("text").setStyleClass("tile_text");
        
        tileitem = getElement("tileitem");
        tileitem.setAction("task");
        tileitem.setStyleClass("tile_item");
    }
    
}