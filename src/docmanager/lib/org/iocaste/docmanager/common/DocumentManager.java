package org.iocaste.docmanager.common;

import java.util.Collection;
import java.util.Map;

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

    public final boolean exists(String cmodelname, Object code) {
        Message message = new Message("exists");
        message.add("cmodel_name", cmodelname);
        message.add("code", code);
        return call(message);
    }
    
    public final ComplexDocument get(String cmodelname, Object ns, Object code)
    {
        Message message = new Message("get");
        message.add("cmodel_name", cmodelname);
        message.add("code", code);
        message.add("ns", ns);
        return call(message);
    }
    
    public final ComplexDocument save(String cmodelname, Object ns,
            ExtendedObject head, Collection<ExtendedObject[]> groups,
            Map<String, Integer> itemsdigits) {
        Message message = new Message("save");
        message.add("cmodel_name", cmodelname);
        message.add("head", head);
        message.add("groups", groups);
        message.add("ns", ns);
        message.add("item_digits", itemsdigits);
        return call(message);
    }
}
