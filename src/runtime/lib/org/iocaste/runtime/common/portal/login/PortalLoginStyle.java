package org.iocaste.runtime.common.portal.login;

import org.iocaste.runtime.common.style.AbstractViewConfigStyle;

public class PortalLoginStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        load("#nc_inner_login");
        put("display", "none");
        forEachMedia(new PortalLoginStyleMedia());
    }
    
}

