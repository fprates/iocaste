package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
//        DashboardFactory factory;
//        NavControl navcontrol;
//        
//        navcontrol = getNavControl();
//        navcontrol.setTitle("SYSINFO");
//        
//        factory = getDashboard("menu");
//        factory.setRenderer(new MenuRenderer());
    }
}

//class MenuRenderer extends StandardDashboardRenderer {
//    
//    public final void config(String name) {
//        String outerstyle;
//        
//        super.config(name);
//        outerstyle = getStyle(name, OUTER);
//        stylesheet.get(outerstyle).put("width", "100%");
//    }
//}