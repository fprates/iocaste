package org.iocaste.exhandler;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        NavControl navcontrol;
        
        extcontext = getExtendedContext();
        navcontrol = getNavControl();
        navcontrol.setTitle(extcontext.messages.get("exception-handler"));
    }

}
