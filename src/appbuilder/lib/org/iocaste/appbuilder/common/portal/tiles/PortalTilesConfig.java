package org.iocaste.appbuilder.common.portal.tiles;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.appbuilder.common.tiles.AbstractTileInput;
import org.iocaste.appbuilder.common.tiles.TilesData;
import org.iocaste.shell.common.NodeList;

public class PortalTilesConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TilesData tiles;
        NodeList nodes;
        PortalContext extcontext;
        
        nodes = getElement("viewport");
        nodes.setStyleClass("portal_viewport");
        nodes.setItemsStyle("portal_viewport_node");
        
        extcontext = getExtendedContext();
        tiles = getTool("items");
        tiles.spec = extcontext.pagetiles.spec;
        tiles.config = extcontext.pagetiles.config;
        tiles.input = (AbstractTileInput)extcontext.pagetiles.input;
    }
    
}
