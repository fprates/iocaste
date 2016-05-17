package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tiles.TilesData;

public class GroupsConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TilesData tiles;
        
        tiles = getTool("items");
        tiles.spec = new GroupsTileSpec();
        tiles.config = new GroupsTileConfig();
        tiles.input = new GroupsTileInput();
    }
    
}
