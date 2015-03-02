package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public class TaskInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        DataElement taskname, taskcommand;
        ModelInstall model;

        taskname = elementchar("TASKS.NAME", 18, DataType.UPPERCASE);
        taskcommand = elementchar("TASKS.COMMAND", 128, DataType.KEEPCASE);
        
        model = modelInstance("TASKS", "TASKS");
        tag("taskname", model.key("NAME", "TSKNM", taskname));
        model.item("COMMAND", "CMDLN", taskcommand);
    }

}
