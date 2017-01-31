package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.portal.PortalStyle;
import org.iocaste.appbuilder.common.portal.tiles.AbstractTilesPage;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemConfig;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemData;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemInput;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemSpec;

public class TasksPanelPage extends AbstractTilesPage {

    @Override
    protected void entry() throws Exception {
        PortalTileItemData tiledata;
        
        tiledata = new PortalTileItemData();
        tiledata.key = "NAME";
        tiledata.show.add("NAME");
        tiledata.show.add("TEXT");
        
        set(new PortalTileItemSpec(tiledata));
        set(new PortalTileItemConfig(tiledata));
        set(new PortalTileItemInput(tiledata));
        set(new PortalStyle());
        put("pick", new Call());
        update();
    }
}
