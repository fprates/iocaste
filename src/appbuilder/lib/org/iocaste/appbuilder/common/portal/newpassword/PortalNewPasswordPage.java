package org.iocaste.appbuilder.common.portal.newpassword;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class PortalNewPasswordPage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new PortalSignUpSpec());
        set(new PortalNewPasswordConfig());
        set(new PortalNewPasswordInput());
    }

}
