package org.iocaste.copy;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class Send extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Documents documents;
        ExtendedObject[] objects = message.get("objects");
        
        documents = new Documents(getFunction());
        for (ExtendedObject object : objects)
            documents.save(object);
        return null;
    }

}
