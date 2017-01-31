package org.iocaste.appbuilder.common.portal.tiles;

import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class PortalTileItemSpec extends AbstractViewSpec {
    private PortalTileItemData data;
    
    public PortalTileItemSpec() {
        this(null);
    }
    
    public PortalTileItemSpec(PortalTileItemData data) {
        this.data = data;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        String nodeitem;
        Set<String> show;
        
        link(parent, "item");
        nodelist("item", "node_item");
        show = PortalTileItemData.showset(getExtendedContext(), data);
        for (String name : show) {
            nodeitem = name.concat("_node");
            nodelistitem("node_item", nodeitem);
            text(nodeitem, name);
        }   
    }
}
