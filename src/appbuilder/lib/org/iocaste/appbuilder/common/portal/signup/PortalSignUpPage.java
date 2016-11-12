package org.iocaste.appbuilder.common.portal.signup;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.appbuilder.common.portal.PortalStyle;
import org.iocaste.appbuilder.common.portal.login.PortalConnect;

public class PortalSignUpPage extends AbstractPanelPage {
    
    @Override
    public void execute() throws Exception {
        PortalContext extcontext = getExtendedContext();
        
        set(new PortalSignUpSpec());
        set(new PortalSignUpConfig());
        set(new PortalSignUpInput());
        set(new PortalStyle());
        put("record", new PortalSignUpSave());
        put("connect", new PortalConnect());
        put("load", extcontext.load);
    }

}
