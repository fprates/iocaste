package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;
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
     * @param shname
     * @param itemname
     * @throws Exception
     */
    public final void assign(DocumentModelItem item) throws Exception {
        Message message = new Message();
        
        message.setId("assign");
        message.add("model_item", item);
        
        call(message);
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
        
        return call(message);
    }
    
    /**
     * 
     * @param shname
     * @return
     * @throws Exception
     */
    public final int remove(String name) throws Exception {
        Message message = new Message();
        
        message.setId("remove");
        message.add("shname", name);
        
        return call(message);
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
     * @param shname
     * @return
     * @throws Exception
     */
    public final int unassign(String shname) throws Exception {
        Message message = new Message();
        
        message.setId("unassign");
        message.add("name", shname);
        
        return call(message);
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
