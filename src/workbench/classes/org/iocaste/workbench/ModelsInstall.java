package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;

public class ModelsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall project;
        DataElement projectid;
        
        projectid = elementchar("WB_PROJECT_ID", 12, true);
        
        project = modelInstance("WB_PROJECT");
        project.item("PROJECT_ID", projectid);
    }

}
