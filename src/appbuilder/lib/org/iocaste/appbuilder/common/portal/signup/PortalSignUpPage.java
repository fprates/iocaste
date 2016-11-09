package org.iocaste.appbuilder.common.portal.signup;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class PortalSignUpPage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new PortalSignUpSpec());
        set(new PortalSignUpConfig());
        set(new PortalSignUpInput());
    }

}
