package org.iocaste.appbuilder.common.portal.tiles;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tiles.AbstractTileInput;
import org.iocaste.appbuilder.common.tiles.TilesData;
import org.iocaste.shell.common.NodeList;

public class PortalTilesConfig extends AbstractViewConfig {
    public PortalPageTiles pagetiles;

    @Override
    protected void execute(PageBuilderContext context) {
        TilesData tiles;
        NodeList nodes;
        
        nodes = getElement("viewport");
        nodes.setStyleClass("portal_viewport");
        nodes.setItemsStyle("portal_viewport_node");
        
        tiles = getTool("items");
        tiles.spec = pagetiles.spec;
        tiles.config = pagetiles.config;
        tiles.input = (AbstractTileInput)pagetiles.input;
    }
    
}
