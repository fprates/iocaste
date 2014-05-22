package org.iocaste.docmanager.common;

import java.util.Collection;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class DocumentManager extends AbstractServiceInterface {
    public static final String SERVICE = "/iocaste-docmanager/services.html";
    
    public DocumentManager(Function function) {
        initService(function, SERVICE);
    }

    public final boolean exists(String cmodelname, String code) {
        Message message = new Message("exists");
        message.add("cmodel_name", cmodelname);
        message.add("code", code);
        return call(message);
    }
    
    public final ComplexDocument get(String cmodelname, String code) {
        Message message = new Message("get");
        message.add("cmodel_name", cmodelname);
        message.add("code", code);
        return call(message);
    }
    
    public final void save(String cmodelname, ExtendedObject head,
            Collection<ExtendedObject[]> groups) {
        Message message = new Message("save");
        message.add("cmodel_name", cmodelname);
        message.add("head", head);
        message.add("groups", groups);
        call(message);
    }
}
