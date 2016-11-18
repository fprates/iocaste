package org.iocaste.appbuilder.common.portal.login;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.appbuilder.common.portal.PortalStyle;

public class PortalLoginPage extends AbstractPanelPage {
    
    @Override
    public void execute() throws Exception {
        PortalContext extcontext = getExtendedContext();
        
        set(new PortalLoginSpec());
        set(new PortalLoginConfig());
        set(new StandardViewInput());
        set(new PortalStyle());
        put("signup", new PortalSignUp());
        put("connect", new PortalConnect());
        put("newpassword", new PortalNewPassword());
        put("load", extcontext.load);
    }
}