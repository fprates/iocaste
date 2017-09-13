package org.iocaste.runtime.common.portal.login;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.shell.common.tooldata.ToolData;

public class PortalLoginConfig extends AbstractViewConfig<Context> {

    @Override
    protected void execute(Context context) {
        ToolData nodes, button, login, item, link;
        
        nodes = getTool("viewport");
        nodes.style = "portal_mini_viewport";
        nodes.itemstyle = "portal_viewport_node";
        
        button = getTool("connect");
        button.style = "portal_button";
        button.submit = true;
        
        login = getTool("login");
        login.model = "PORTAL_USER_INPUT";
        login.internallabel = true;
        show(login, "EMAIL", "SECRET");
        
        item = login.instance("EMAIL");
        item.label = "login_email";
        
        item = login.instance("SECRET");
        item.secret = true;
        item.label = "login_secret";
        
        link = getTool("signup");
        link.style = "portal_login_option";
        link.cancellable = true;
        
        link = getTool("newpassword");
        link.style = "portal_login_option";
        link.cancellable = true;
    }
    
}