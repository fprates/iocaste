package org.iocaste.runtime.common.portal.tiles;

import org.iocaste.runtime.common.page.AbstractPage;

public class PortalTileItemPage extends AbstractPage {

    @Override
    protected void execute() throws Exception {
        set(new PortalTileItemSpec());
        set(new PortalTileItemConfig());
    }

}
