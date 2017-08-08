package org.iocaste.runtime.common.portal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.portal.tiles.PortalPageTiles;
import org.iocaste.runtime.common.application.AbstractContext;

public class PortalContext extends AbstractContext {
    public String email, secret, userprofile, username;
    public Map<String, PortalPageTiles> pagetiles;
    
    public PortalContext() {
    	pagetiles = new HashMap<>();
    }

}
