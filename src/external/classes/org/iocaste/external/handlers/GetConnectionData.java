package org.iocaste.external.handlers;

import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetConnectionData extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Documents documents;
        String name;
        
        name = message.getst("name");
        if (name == null)
            throw new IocasteException("connection name not specified.");
        
        documents = new Documents(getFunction());
        return documents.getComplexDocument("XTRNL_CONNECTION", null, name);
    }

}
