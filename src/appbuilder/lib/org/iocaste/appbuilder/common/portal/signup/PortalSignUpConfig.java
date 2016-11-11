package org.iocaste.appbuilder.common.portal.signup;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.shell.common.NodeList;

public class PortalSignUpConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        NodeList nodes;
        AbstractComponentData tool;
        DataFormToolItem item;
        
        nodes = getElement("viewport");
        nodes.setStyleClass("portal_viewport");
        nodes.setItemsStyle("portal_viewport_node");
        
        tool = getTool("user");
        tool.model = "PORTAL_USER_INPUT";
        item = tool.instance("SECRET");
        item.secret = true;
        
        getElement("record").setStyleClass("portal_button");
    }
    
}

