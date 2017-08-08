package org.iocaste.runtime.common.portal.login;

import org.iocaste.runtime.common.style.AbstractViewConfigStyle;

public class PortalLoginStyleMedia extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        load(".portal_viewport_node");
        put("text-align", "center");
    }
    
}
