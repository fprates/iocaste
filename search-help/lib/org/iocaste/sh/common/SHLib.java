package org.iocaste.sh.common;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.SearchHelp;

public class SHLib extends AbstractServiceInterface {
    private static final String APP_NAME = "/iocaste-search-help/services.html";
    
    public SHLib(Function function) {
        initService(function, APP_NAME);
    }
    
    /**
     * 
     * @param container
     * @param name
     * @return
     * @throws Exception
     */
    public final SearchHelp get(Container container, String name)
            throws Exception {
        Message message = new Message();
        
        message.setId("get");
        message.add("name", name);
        message.add("container", container);
        
        return (SearchHelp)call(message);
    }
    
    /**
     * 
     * @param object
     * @param itens
     * @throws Exception
     */
    public final void save(ExtendedObject object, ExtendedObject...itens)
            throws Exception {
        Message message = new Message();
        
        message.setId("save");
        message.add("header", object);
        message.add("itens", itens);
        
        call(message);
    }

}
