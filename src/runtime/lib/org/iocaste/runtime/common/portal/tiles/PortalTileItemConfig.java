package org.iocaste.runtime.common.portal.tiles;

import java.util.Set;

import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.runtime.common.portal.PortalContext;

public class PortalTileItemConfig extends AbstractViewConfig<PortalContext> {
    
    @Override
    protected void execute(PortalContext context) {
        PortalTilesData data;
        ToolData link;
        Set<String> show;
        boolean first = true;
        
        getTool("node_item").style = "portal_tile_frame";
        
        data = context.tilesDataInstance();
        link = getTool("item");
        link.actionname = data.action;
        link.action = (data.action == null);
        
        show = PortalTilesData.showset(context, data);
        for (String name : show) {
            if ((((data == null) || data.key == null) && first) ||
                    ((data != null) && (data.key.equals(name)))) {
                getTool(name).style = "portal_tile_key";
                link.modelitem = name;
                first = false;
            } else {
                getTool(name).style = "portal_tile_text";
            }
        }
    }
}