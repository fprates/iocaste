package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tiles.TilesData;

public class TasksConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TilesData tiles;
        
        tiles = getTool("items");
        tiles.spec = new TasksTileSpec();
        tiles.config = new TasksTileConfig();
        tiles.input = new TasksTileInput();
    }
    
}
