package org.iocaste.appbuilder.common.portal.tiles;

import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;

public class PortalTileItemConfig extends AbstractViewConfig {
    private PortalTileItemData data;
    
    public PortalTileItemConfig() {
        this(null);
    }
    
    public PortalTileItemConfig(PortalTileItemData data) {
        this.data = data;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        Link link;
        Set<String> show;
        boolean first = true;
        
        getElement("node_item").setStyleClass("portal_tile_frame");
        link = getElement("item");
        link.setAction("pick");

        show = PortalTileItemData.showset(getExtendedContext(), data);
        for (String name : show)
            if ((((data == null) || data.key == null) && first) ||
                    ((data != null) && (data.key.equals(name)))) {
                getElement(name).setStyleClass("portal_tile_key");
                first = false;
            } else {
                getElement(name).setStyleClass("portal_tile_text");
            }
    }
}