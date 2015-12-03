package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;

public class ReportToolFactory extends AbstractSpecFactory {
    
    @Override
    protected AbstractComponentData dataInstance() {
        return new ReportToolData(context);
    }
    
    @Override
    public final void generate(ComponentEntry entry) {
        entry.component = new ReportTool(entry);
    }
}
