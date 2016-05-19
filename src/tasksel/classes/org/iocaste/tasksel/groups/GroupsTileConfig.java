package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;
import org.iocaste.tasksel.Style;

public class GroupsTileConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Link tileitem;
        
        Style.set(context);
        
        getElement("frame").setStyleClass("tile_frame");
        getElement("name").setStyleClass("tile_text");
        
        tileitem = getElement("tileitem");
        tileitem.setAction("group");
        tileitem.setStyleClass("tile_item");
    }
    
}