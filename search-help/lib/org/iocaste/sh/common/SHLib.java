package org.iocaste.sh.common;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class SHLib extends AbstractServiceInterface {
    private static final String APP_NAME = "/iocaste-search-help/services.html";
    
    public SHLib(Function function) {
        initService(function, APP_NAME);
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public final ExtendedObject[] get(String name) throws Exception {
        Message message = new Message();
        
        message.setId("get");
        message.add("name", name);
        
        return (ExtendedObject[])call(message);
    }
    
    /**
     * 
     * @param header
     * @param itens
     * @throws Exception
     */
    public final void save(ExtendedObject header, ExtendedObject...itens)
            throws Exception {
        Message message = new Message();
        
        message.setId("save");
        message.add("header", header);
        message.add("itens", itens);
        
        call(message);
    }
    
    /**
     * 
     * @param header
     * @param itens
     * @throws Exception
     */
    public final void update(ExtendedObject header, ExtendedObject... itens)
            throws Exception {
        Message message = new Message();
        
        message.setId("update");
        message.add("header", header);
        message.add("itens", itens);
        
        call(message);
    }

}
