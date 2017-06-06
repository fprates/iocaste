package org.iocaste.kernel.database;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetConnectionPoolInfo extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Database services = getFunction();
        return services.getConnections();
    }

}
