package org.iocaste.infosys.main;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.infosys.InfosysContext;
import org.iocaste.protocol.database.ConnectionInfo;
import org.iocaste.runtime.common.application.AbstractActionHandler;

public class GetConnectionInfo extends AbstractActionHandler<InfosysContext> {

    @Override
    protected void execute(InfosysContext context) throws Exception {
        ExtendedObject object;
        ConnectionInfo[] connections = context.runtime().
                getConnectionPoolInfo();
        for (ConnectionInfo connection : connections) {
            object = instance("IS_CONNECTION");
            object.set("CONNECTION_ID", connection.connid);
            object.set("CREATED_ON", connection.createdon);
            object.set("ASSIGNED_TO", connection.sessionid);
            context.add("connections", object);
        }
    }
    
}
