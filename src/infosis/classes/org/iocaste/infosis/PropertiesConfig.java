package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;

public class PropertiesConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        ReportToolData reporttool;
        NavControl navcontrol = getNavControl();
        
        extcontext = getExtendedContext();
        navcontrol.setTitle(extcontext.title);
        
        reporttool = getReportTool("properties");
        reporttool.model = extcontext.model.getName();
    }

}
