package org.iocaste.documents.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Documents extends AbstractServiceInterface {
    private static final String SERVERNAME = "/iocaste-documents/services.html";
    
    public Documents(Function function) {
        initService(function, SERVERNAME);
    }
    
    /**
     * 
     * @param range
     * @return
     * @throws Exception
     */
    public final long getNextNumber(String range) throws Exception {
        Message message = new Message();
        
        message.setId("get_next_number");
        message.add("range", range);
        
        return (Long)call(message);
    }
}
