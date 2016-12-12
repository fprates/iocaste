package org.iocaste.install;

import org.iocaste.internal.ProcessHttpRequisition;
import org.iocaste.internal.RendererContext;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Iocaste;

public class ProcessInstallHttpRequisition extends ProcessHttpRequisition {

    public ProcessInstallHttpRequisition() {
        disconnecteddb = true;
    }

    @Override
    public final void run(RendererContext context) throws Exception {
        GenericService service;
        Message message;
        
        message = new Message("disconnected_operation");
        message.add("disconnected", true);
        service = new GenericService(getFunction(), Iocaste.SERVERNAME);
        service.invoke(message);
        
        super.run(context);
        
        message.add("disconnected", false);
        service.invoke(message);
    }
}
