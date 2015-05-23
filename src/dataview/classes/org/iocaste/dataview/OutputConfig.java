package org.iocaste.dataview;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;

public class OutputConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Map<String, String> style;
        Context extcontext = getExtendedContext();
        ReportToolData reporttool = getReportTool("items");
        
        reporttool.model = extcontext.model.getName();
        getNavControl().setTitle(reporttool.model);
        
        style = context.view.styleSheetInstance().get(".std_panel_content");
        style.put("overflow", "auto");
        style.put("width", "100%");
        style.put("height", "100%");
        style.put("position", "relative");
    }

}
