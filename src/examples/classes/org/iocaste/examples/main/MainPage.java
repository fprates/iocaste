package org.iocaste.examples.main;

import org.iocaste.appbuilder.common.portal.PortalStyle;
import org.iocaste.appbuilder.common.portal.tiles.AbstractTilesPage;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemConfig;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemInput;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemSpec;

public class MainPage extends AbstractTilesPage {

    @Override
    protected void entry() throws Exception {
        set(new PortalTileItemSpec());
        set(new PortalTileItemConfig());
        set(new PortalTileItemInput());
        set(new PortalStyle());
        put("select", new MainSelect());
        put("pick", new MainPick());
        
        run("select");
    }
    
}
