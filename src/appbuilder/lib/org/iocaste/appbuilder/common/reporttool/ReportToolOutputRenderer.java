package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class ReportToolOutputRenderer {

    public static final void execute(ReportToolData data) {
        TableToolData ttdata;
        
        ttdata = new TableToolData();
        ttdata.context = data.context;
        ttdata.container = data.name;
        ttdata.name = data.name;
        ttdata.mode = TableTool.DISPLAY;
        ttdata.vlines = 0;
        
        new TableTool(ttdata);
    }
}
