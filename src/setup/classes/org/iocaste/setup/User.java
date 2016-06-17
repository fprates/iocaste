package org.iocaste.setup;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.packagetool.common.SearchHelpData;

public class User extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        DataElement username, taskname;
        SearchHelpData shd;
        
        username = new DummyElement("LOGIN.USERNAME");
        taskname = new DummyElement("TASKS.NAME");
        model = modelInstance("LOGIN_EXTENSION", "LOGINEXT");
        model.key("USERNAME", "USRNM", username);
        searchhelp(model.item("TASK", "TSKNM", taskname), "SH_TASKS");
        
        shd = searchHelpInstance("SH_TASKS", "TASKS");
        shd.setExport("NAME");
        shd.add("NAME");
        shd.add("COMMAND");
    }
}
