package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardComponent;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DashboardComponent dashitem;
        NavControl navcontrol;
        
        navcontrol = getNavControl();
        navcontrol.setTitle("SYSINFO");
        
        dashitem = getDashboardItem("menu", "items");
        dashitem.add("java-properties");
        dashitem.add("system-info");
        dashitem.add("users-list");
        dashitem.add("unauthorized-accesses");
    }

}
