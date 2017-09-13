package org.iocaste.runtime.common.portal.tiles;

import java.util.Set;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.runtime.common.portal.PortalContext;
import org.iocaste.shell.common.tooldata.ToolData;

public class PortalTileItemConfig extends AbstractViewConfig<Context> {
    
    @Override
    protected void execute(Context context) {
        PortalTilesData data;
        ToolData link;
        Set<String> show;
        PortalContext portalctx = context.portalctx();
        
        getTool("node_item").style = "portal_tile_frame";
        
        data = portalctx.tilesDataInstance();
        link = getTool("item");
        link.actionname = data.action;
        link.action = (data.action == null);
        link.indexitem = data.key;
        
        getTool(data.highlight).style = "portal_tile_key";
        show = PortalTilesData.showset(context, data);
        for (String name : show)
            if (!name.equals(data.highlight))
                getTool(name).style = "portal_tile_text";
    }
}