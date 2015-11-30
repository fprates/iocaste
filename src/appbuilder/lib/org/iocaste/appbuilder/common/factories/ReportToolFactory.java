package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.reporttool.ReportToolEntry;
import org.iocaste.shell.common.StandardContainer;

public class ReportToolFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        ReportToolData rtdata;
        
        rtdata = new ReportToolData(context, name);
        new StandardContainer(container, rtdata.name);
        components.add(rtdata);
    }
    
    @Override
    public final void generate() {
        for (ReportToolEntry entry : components.reporttools.values())
            entry.component = new ReportTool(entry.data);
    }
    
    @Override
    public final void update() {
        for (ReportToolEntry entry :  components.reporttools.values())
            if (entry.update)
                entry.component.refresh();
    }

}
