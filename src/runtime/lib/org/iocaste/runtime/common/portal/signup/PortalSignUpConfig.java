package org.iocaste.runtime.common.portal.signup;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.shell.common.tooldata.ToolData;

public class PortalSignUpConfig extends AbstractViewConfig<Context> {

    @Override
    protected void execute(Context context) {
        ToolData tool, nodes;
        
        nodes = getTool("viewport");
        nodes.style = "portal_viewport";
        nodes.styles.put("item", "portal_viewport_node");
        
        tool = getTool("user");
        tool.model = "PORTAL_USER_INPUT";
        tool.instance("SECRET").secret = true;
    }
    
}

