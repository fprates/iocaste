package org.iocaste.runtime.common.portal.tiles;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.shell.common.tooldata.ToolData;

public class PortalTilesConfig extends AbstractViewConfig<Context> {

    @Override
    protected void execute(Context context) {
        ToolData tiles, nodes;
        
        nodes = getTool("viewport");
        nodes.style = "portal_viewport";
        nodes.styles.put("item", "portal_viewport_node");
//        getNavControl().setTitle(pagetiles.title, pagetiles.titleargs);
        
        tiles = getTool("items");
        tiles.subpage = "portaltilesitem";
    }
    
}
