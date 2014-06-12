package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;

public class ModelsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        DataElement projectid;
        
        projectid = elementchar("WB_PROJECT_ID", 12, false);
        model = modelInstance("WB_PROJECT");
        model.item("PROJECT_ID", projectid);
    }

}
