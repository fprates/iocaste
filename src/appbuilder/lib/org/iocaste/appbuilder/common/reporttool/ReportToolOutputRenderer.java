package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class ReportToolOutputRenderer {

    public static final void run(ReportToolData data) {
        data.output.ttdata = new TableToolData();
        data.output.ttdata.context = data.context;
        data.output.ttdata.container = data.name;
        data.output.ttdata.name = data.name;
        data.output.ttdata.mode = TableTool.DISPLAY;
        data.output.ttdata.vlines = 0;
        
        data.output.tabletool = new TableTool(data.output.ttdata);
    }
}
