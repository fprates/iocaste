package org.iocaste.infosys.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.runtime.common.install.AbstractInstallObject;
import org.iocaste.runtime.common.install.InstallContext;
import org.iocaste.runtime.common.install.ModelInstall;

public class ConnectionsInstall extends AbstractInstallObject {

    @Override
    protected void execute(InstallContext context) throws Exception {
        ModelInstall model;
        DataElement connectionid, createdon, assignedto;
        
        connectionid = elementnumc("IS_CONNECTION_ID", 5);
        createdon = elementdate("IS_CREATED_ON");
        assignedto = elementchar("IS_ASSIGNED_TO", 64, DataType.KEEPCASE);
        
        model = modelInstance("IS_CONNECTION");
        model.item("CONNECTION_ID", connectionid);
        model.item("CREATED_ON", createdon);
        model.item("ASSIGNED_TO", assignedto);
    }
    
}
