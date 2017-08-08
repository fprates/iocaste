package org.iocaste.runtime.common.portal.login;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewSpec;

public class PortalLoginSpec extends AbstractViewSpec {

    @Override
    protected void execute(Context context) {
        nodelist(parent, "viewport");
        nodelistitem("viewport", "login_node");
        dataform("login_node", "login");
        button("login_node", "connect");
        
        nodelistitem("viewport", "signup_node");
        link("signup_node", "signup");
        
        nodelistitem("viewport", "new_password_node");
        link("new_password_node", "newpassword");
    }
    
}

