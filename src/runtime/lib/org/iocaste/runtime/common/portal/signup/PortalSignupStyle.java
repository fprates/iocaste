package org.iocaste.runtime.common.portal.signup;

import org.iocaste.runtime.common.style.AbstractViewConfigStyle;

public class PortalSignupStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        load("#nc_inner_login");
        put("display", "none");
    }
    
}

