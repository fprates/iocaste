package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.reporttool.ReportToolEntry;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;

public class ReportToolFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        ReportToolData rtdata;
        
        rtdata = new ReportToolData();
        rtdata.context = context;
        rtdata.name = name;
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
