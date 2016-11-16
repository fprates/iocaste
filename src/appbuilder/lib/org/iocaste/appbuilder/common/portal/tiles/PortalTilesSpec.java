package org.iocaste.appbuilder.common.portal.tiles;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class PortalTilesSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        nodelist(parent, "viewport");
        nodelistitem("viewport", "items_node");
        tiles("items_node", "items");
    }
    
}

