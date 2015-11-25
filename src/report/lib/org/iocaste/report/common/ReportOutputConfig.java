package org.iocaste.report.common;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.report.common.data.ReportViewerData;

public class ReportOutputConfig extends AbstractViewConfig {
    private ReportViewerData data;
    
    public ReportOutputConfig(ReportViewerData data) {
        this.data = data;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        NavControl navcontrol;
        
        config(data.output.config);
        if (data.export == null)
            return;
        navcontrol = getNavControl();
        navcontrol.add("csv");
        navcontrol.noScreenLockFor("csv");
    }

}
