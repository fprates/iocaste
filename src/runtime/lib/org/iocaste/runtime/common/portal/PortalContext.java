package org.iocaste.runtime.common.portal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.portal.tiles.PortalTilesData;

public class PortalContext {
    private Context context;
    public String email, secret, userprofile, username;
    public Map<String, PortalTilesData> pagetiles;
    
    public PortalContext(Context context) {
        this.context = context;
        pagetiles = new HashMap<>();
    }
    
    public final PortalTilesData tilesDataInstance() {
        String page = context.getPageName();
        PortalTilesData data = pagetiles.get(page);
        if (data == null)
            pagetiles.put(page, data = new PortalTilesData());
        return data;
    }
}
