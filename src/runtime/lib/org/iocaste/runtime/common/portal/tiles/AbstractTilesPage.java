package org.iocaste.runtime.common.portal.tiles;

import org.iocaste.runtime.common.page.AbstractPage;

public abstract class AbstractTilesPage extends AbstractPage {
    
    protected abstract void entry() throws Exception;
    
    @Override
    public void execute() throws Exception {
        set(new PortalTilesSpec());
        set(new PortalTilesConfig());
        subpage("portaltilesitem", new PortalTileItemPage());
        entry();
    }
}
