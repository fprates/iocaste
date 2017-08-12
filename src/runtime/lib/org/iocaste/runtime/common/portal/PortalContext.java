package org.iocaste.runtime.common.portal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.runtime.common.application.AbstractContext;
import org.iocaste.runtime.common.portal.tiles.PortalTilesData;

public class PortalContext extends AbstractContext {
    public String email, secret, userprofile, username;
    public Map<String, PortalTilesData> pagetiles;
    
    public PortalContext() {
        pagetiles = new HashMap<>();
    }
    
    public final PortalTilesData tilesDataInstance() {
        String page = getPageName();
        PortalTilesData data = pagetiles.get(page);
        if (data == null)
            pagetiles.put(page, data = new PortalTilesData());
        return data;
    }
}
