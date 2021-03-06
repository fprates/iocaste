package org.iocaste.appbuilder.common.portal.login;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class PortalLoginSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
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

