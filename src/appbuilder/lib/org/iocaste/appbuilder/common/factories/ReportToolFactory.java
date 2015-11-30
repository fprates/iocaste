package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.shell.common.StandardContainer;

public class ReportToolFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        ReportToolData rtdata;
        
        rtdata = new ReportToolData(context, name);
        new StandardContainer(container, rtdata.name);
        components.add(rtdata);
    }

}
