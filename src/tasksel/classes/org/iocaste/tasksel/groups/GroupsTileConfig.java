package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;

public class GroupsTileConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Link tileitem;
        
        getElement("frame").setStyleClass("tile_frame");
        getElement("name").setStyleClass("tile_text");
        
        tileitem = getElement("tileitem");
        tileitem.setAction("group");
        tileitem.setStyleClass("nc_tiles_link");
    }
    
}