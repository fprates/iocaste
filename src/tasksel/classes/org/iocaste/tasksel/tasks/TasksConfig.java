package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tiles.TilesData;
import org.iocaste.tasksel.Context;

public class TasksConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TilesData tiles;
        Context extcontext = getExtendedContext();
        
        getNavControl().setTitle(extcontext.group);
        
        tiles = getTool("items");
        tiles.spec = new TasksTileSpec();
        tiles.config = new TasksTileConfig();
        tiles.input = new TasksTileInput();
    }
    
}
