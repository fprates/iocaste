package org.iocaste.copy;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.external.common.External;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Const;

public class Copy extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        GenericService service;
        Message message;
        External external;
        ExtendedObject[] objects;
        Iocaste iocaste;
        String model = getdfst("model", "NAME");
        String ns = getdfst("model", "NAMESPACE");
        String port = getdfst("port", "PORT_NAME");
        Query query = new Query();
        
        query.setModel(model);
        query.setNS(ns);
        objects = select(query);
        if (objects == null) {
            message(Const.WARNING, "no.records");
            return;
        }
        
        external = External.connectionInstance(context.function, port, "pt_BR");
        
        message = new Message("send");
        message.add("objects", objects);
        service = external.serviceInstance("/iocaste-copy/services.html");
        service.invoke(message);
        
        iocaste = new Iocaste(external.connector());
        iocaste.commit();
        
        external.disconnect();
    }

}
