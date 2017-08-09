package org.iocaste.runtime.common.portal.login;

import org.iocaste.runtime.common.page.AbstractPage;

public class PortalLoginPage extends AbstractPage {
    
    @Override
    protected void execute() {
        set(new PortalLoginSpec());
        set(new PortalLoginConfig());
        set(new PortalLoginStyle());
//        context.input = new StandardViewInput();
        put("signup", new PortalSignUp());
        put("connect", new PortalConnect());
        put("newpassword", new PortalNewPassword());
    }
}
