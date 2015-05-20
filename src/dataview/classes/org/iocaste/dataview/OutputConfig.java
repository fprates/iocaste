package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.shell.common.StyleSheet;

public class OutputConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        StyleSheet stylesheet;
        String style;
        Context extcontext = getExtendedContext();
        ReportToolData reporttool = getReportTool("items");
        
        reporttool.model = extcontext.model.getName();
        getNavControl().setTitle(reporttool.model);
        
        style = ".std_panel_content";
        stylesheet = context.view.styleSheetInstance();
        stylesheet.put(style, "overflow", "auto");
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "position", "relative");
    }

}
