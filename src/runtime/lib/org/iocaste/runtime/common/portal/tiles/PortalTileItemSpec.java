package org.iocaste.runtime.common.portal.tiles;

import java.util.Set;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewSpec;

public class PortalTileItemSpec extends AbstractViewSpec {
    private PortalTilesData data;
    
    public PortalTileItemSpec() {
        this(null);
    }
    
    public PortalTileItemSpec(PortalTilesData data) {
        this.data = data;
    }
    
    @Override
    protected void execute(Context context) {
        String nodeitem;
        Set<String> show;
        
        link(parent, "item");
        nodelist("item", "node_item");
        show = PortalTilesData.showset(context, data);
        for (String name : show) {
            nodeitem = name.concat("_node");
            nodelistitem("node_item", nodeitem);
            text(nodeitem, name);
        }   
    }
}
