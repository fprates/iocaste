package org.iocaste.appbuilder.common.portal.signup;

import org.iocaste.appbuilder.common.ActionHandler;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.portal.PortalStyle;
import org.iocaste.appbuilder.common.portal.login.PortalConnect;

public class PortalSignUpPage extends AbstractPanelPage {
    private ActionHandler load;
    
    @Override
    public void execute() throws Exception {
        set(new PortalSignUpSpec());
        set(new PortalSignUpConfig());
        set(new PortalSignUpInput());
        set(new PortalStyle());
        put("record", new PortalSignUpSave());
        put("connect", new PortalConnect());
        put("load", load);
    }
    
    public final void setLoadHandler(ActionHandler handler) {
        load = handler;
    }

}
