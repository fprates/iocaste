package org.iocaste.runtime.common.portal.tiles;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.AbstractViewConfig;

public class PortalTilesConfig extends AbstractViewConfig<Context> {

    @Override
    protected void execute(Context context) {
        ToolData tiles, nodes;
        
        nodes = getTool("viewport");
        nodes.style = "portal_viewport";
        nodes.itemstyle = "portal_viewport_node";
//        getNavControl().setTitle(pagetiles.title, pagetiles.titleargs);
        
        tiles = getTool("items");
        tiles.subpage = "portaltilesitem";
    }
    
}
