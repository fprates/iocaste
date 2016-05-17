package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;

public class OutputConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        ReportToolData reporttool = getTool("items");
        
        reporttool.output.model = extcontext.model.getName();
        getNavControl().setTitle(reporttool.output.model);
    }

}
