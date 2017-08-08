package org.iocaste.runtime.common.portal.login;

import org.iocaste.runtime.common.portal.AbstractPortalPage;

public class PortalLoginPage extends AbstractPortalPage {
    
    public PortalLoginPage() {
        context.spec = new PortalLoginSpec();
        context.config = new PortalLoginConfig();
        context.style = new PortalLoginStyle();
//        context.input = new StandardViewInput();
        context.handlers.put("signup", new PortalSignUp());
        context.handlers.put("connect", new PortalConnect());
        context.handlers.put("newpassword", new PortalNewPassword());
    }
}
