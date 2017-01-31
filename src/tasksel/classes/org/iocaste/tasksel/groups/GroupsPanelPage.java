package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.portal.PortalStyle;
import org.iocaste.appbuilder.common.portal.tiles.AbstractTilesPage;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemConfig;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemData;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemInput;
import org.iocaste.appbuilder.common.portal.tiles.PortalTileItemSpec;

public class GroupsPanelPage extends AbstractTilesPage {

    @Override
    protected void entry() throws Exception {
        PortalTileItemData tilesdata;
        
        tilesdata = new PortalTileItemData();
        tilesdata.key = "GROUP";
        tilesdata.show.add("GROUP");
        tilesdata.show.add("TEXT");
        
        set(new PortalTileItemSpec(tilesdata));
        set(new PortalTileItemConfig(tilesdata));
        set(new PortalTileItemInput(tilesdata));
        set(new PortalStyle());
        put("pick", new GroupsSelect());
        put("lists_get", new GetLists());
        
        run("lists_get");
        update();
    }
}
