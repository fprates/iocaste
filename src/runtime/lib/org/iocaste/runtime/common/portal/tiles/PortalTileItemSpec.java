package org.iocaste.runtime.common.portal.tiles;

import java.util.Set;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewSpec;

public class PortalTileItemSpec extends AbstractViewSpec {
    
    @Override
    protected void execute(Context context) {
        Set<String> show;
        PortalTilesData data;
        
        link(parent, "item");
        nodelist("item", "node_item");
        data = context.portalctx().tilesDataInstance();
        show = PortalTilesData.showset(context, data);
        if (data.highlight == null) {
            if (data.key == null) {
                if (data.highlight == null)
                    for (String key : show) {
                        data.highlight = data.key = key;
                        break;
                    }
            } else {
                data.highlight = data.key;
            }
        }
        
        makeNode(data.highlight);
        for (String name : show)
            if (!name.equals(data.highlight))
                makeNode(name);
    }
    
    private void makeNode(String name) {
        String nodeitem = name.concat("_node");
        nodelistitem("node_item", nodeitem);
        text(nodeitem, name);
    }
}
