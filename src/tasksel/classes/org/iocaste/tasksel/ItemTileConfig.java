package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;

public class ItemTileConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Link tileitem;
        
        getElement("frame").setStyleClass("tilestyle");
        
        tileitem = getElement("tileitem");
        tileitem.setAction("group");
    }
    
}