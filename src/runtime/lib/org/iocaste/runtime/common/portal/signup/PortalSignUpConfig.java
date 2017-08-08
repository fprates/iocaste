package org.iocaste.runtime.common.portal.signup;

import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.runtime.common.portal.PortalContext;

public class PortalSignUpConfig extends AbstractViewConfig<PortalContext> {

    @Override
    protected void execute(PortalContext context) {
        ToolData tool, item, nodes;
        
        nodes = getTool("viewport");
        nodes.style = "portal_viewport";
        nodes.itemstyle = "portal_viewport_node";
        
        tool = getTool("user");
        tool.model = "PORTAL_USER_INPUT";
        item = tool.instance("SECRET");
        item.secret = true;
        
        getTool("record").style = "portal_button";
    }
    
}

