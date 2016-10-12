package org.iocaste.appbuilder.common.portal.login;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeList;

public class PortalLoginConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Link link;
        DataFormToolData tool;
        DataFormToolItem item;
        NodeList nodes;
//        PortalContext extcontext;
//        String page = context.view.getPageName();
        
//        context.view.setTitle(extcontext.get(page).title);
        
        nodes = getElement("viewport");
        nodes.setStyleClass("portal_viewport");
        nodes.setItemsStyle("portal_viewport_node");
        
        getElement("connect").setStyleClass("portal_button");
        
        tool = getTool("login");
        tool.model = "PORTAL_USERS";
        tool.internallabel = true;
        show(tool, "EMAIL", "SECRET");
        
        item = tool.instance("EMAIL");
        item.label = "login_email";
        item.required = true;
        
        item = tool.instance("SECRET");
        item.secret = true;
        item.label = "login_secret";
        item.required = true;
        
        link = getElement("signup");
        link.setStyleClass("portal_login_option");
        link.setCancellable(true);
        
        link = getElement("forgot_user");
        link.setStyleClass("portal_login_option");
        link.setCancellable(true);
    }
    
}