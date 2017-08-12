package org.iocaste.runtime.common.portal.tiles;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewSpec;

public class PortalTilesSpec extends AbstractViewSpec {

    @Override
    protected void execute(Context context) {
        nodelist(parent, "viewport");
        nodelistitem("viewport", "items_node");
        tiles("items_node", "items");
    }
    
}

