package org.iocaste.runtime.common.portal.tiles;

import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.runtime.common.portal.PortalContext;

public class PortalTilesConfig extends AbstractViewConfig<PortalContext> {

    @Override
    protected void execute(PortalContext context) {
        ToolData tiles, nodes;
        
        nodes = getTool("viewport");
        nodes.style = "portal_viewport";
        nodes.itemstyle = "portal_viewport_node";
//        getNavControl().setTitle(pagetiles.title, pagetiles.titleargs);
        
        tiles = getTool("items");
        tiles.subpage = "portaltilesitem";
    }
    
}
