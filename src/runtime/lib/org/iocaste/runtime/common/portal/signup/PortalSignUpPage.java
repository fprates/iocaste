package org.iocaste.runtime.common.portal.signup;

import org.iocaste.runtime.common.portal.AbstractPortalPage;
import org.iocaste.runtime.common.portal.login.PortalConnect;

public class PortalSignUpPage extends AbstractPortalPage {
    
    public PortalSignUpPage() {
        context.spec = new PortalSignUpSpec();
        context.config = new PortalSignUpConfig();
        context.style = new PortalSignupStyle();
//        set(new PortalSignUpInput());
        context.handlers.put("record", new PortalSignUpSave());
        context.handlers.put("connect", new PortalConnect());
    }

}
