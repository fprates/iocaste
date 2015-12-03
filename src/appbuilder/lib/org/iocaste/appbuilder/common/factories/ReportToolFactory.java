package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;

public class ReportToolFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        ReportToolData rtdata;
        
        rtdata = new ReportToolData(context);
        rtdata.name = name;
        new StandardContainer(container, rtdata.name);
        components.add(rtdata);
    }
    
    @Override
    public final void generate(ComponentEntry entry) {
        entry.component = new ReportTool(entry);
    }
}
