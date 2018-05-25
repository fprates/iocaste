package org.iocaste.runtime.common.portal.signup;

import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.portal.login.PortalConnect;

public class PortalSignUpPage extends AbstractPage {
    
    @Override
    protected void execute() {
        set(new PortalSignUpSpec());
        set(new PortalSignUpConfig());
        set(new PortalSignupStyle());
//        set(new PortalSignUpInput());
        action("record", new PortalSignUpSave());
        put("connect", new PortalConnect());
    }

}
